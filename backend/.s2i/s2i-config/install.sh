#!/usr/bin/env bash
source /usr/local/s2i/install-common.sh

injected_dir=$1
echo "Running on injected_dir=${injected_dir}"

run_cli_script "${injected_dir}/setup.cli"

echo "End CLI configuration"