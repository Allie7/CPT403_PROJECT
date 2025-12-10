#!/bin/zsh
# Compile and run the SmartHome specification tests against every branch.

set -uo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
FRAMEWORK_SRC="$ROOT_DIR/src/framework"
TEST_SRC="$ROOT_DIR/src/tests"

typeset -a BRANCH_SOURCES=(
  "main:$(cd "$(dirname "$0")/.." && pwd)/SmartHomeManagementSystem/src"
  "xinyiji:/Users/mac/Desktop/CPT403_PROJECT/CPT403_PROJECT-XinyiJi/SmartHomeManagementSystem/src"
  "yuxuanxie:/Users/mac/Desktop/CPT403_PROJECT/CPT403_PROJECT-YuxuanXie/SmartHomeManagementSystem/src"
)

overall_status=0

if ! command -v javac >/dev/null 2>&1; then
  echo "javac not found. Install a JDK (17+ recommended) before running the tests." >&2
  exit 1
fi

if ! command -v java >/dev/null 2>&1; then
  echo "java not found. Install a JDK (17+ recommended) before running the tests." >&2
  exit 1
fi

compile_sources() {
  local source_dir="$1"
  local output_dir="$2"
  local arg_file="$output_dir/sources.txt"

  find "$source_dir" -name '*.java' | sort > "$arg_file"
  if [[ ! -s "$arg_file" ]]; then
    echo "No Java sources found under $source_dir" >&2
    return 1
  fi
  javac -d "$output_dir" @"$arg_file"
}

for entry in "${BRANCH_SOURCES[@]}"; do
  branch="${entry%%:*}"
  source_dir="${entry#*:}"
  echo ""
  echo "=== Building branch: $branch ==="

  branch_build_dir="$ROOT_DIR/build/$branch"
  branch_classes_dir="$branch_build_dir/classes"
  test_classes_dir="$branch_build_dir/tests"
  mkdir -p "$branch_classes_dir" "$test_classes_dir"

  if ! compile_sources "$source_dir" "$branch_classes_dir"; then
    echo "Failed to compile branch sources for $branch" >&2
    overall_status=1
    continue
  fi

  framework_arg_file="$branch_build_dir/framework_sources.txt"
  test_arg_file="$branch_build_dir/test_sources.txt"
  find "$FRAMEWORK_SRC" -name '*.java' | sort > "$framework_arg_file"
  find "$TEST_SRC" -name '*.java' | sort > "$test_arg_file"

  if ! javac -cp "$branch_classes_dir" -d "$test_classes_dir" @"$framework_arg_file" @"$test_arg_file"; then
    echo "Failed to compile tests for $branch" >&2
    overall_status=1
    continue
  fi

  echo "Running specification tests for $branch..."
  if ! java -cp "$branch_classes_dir:$test_classes_dir" com.yulusi.tests.suites.SmartHomeSpecificationTests "$branch"; then
    echo "Tests failed for $branch" >&2
    overall_status=1
  fi
done

exit "$overall_status"
