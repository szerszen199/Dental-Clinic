#!/usr/bin/env bash
echo "setupsh"
source /usr/local/s2i/install-common.sh
dir=$1
run_cli_script "${dir}/setup.cli"
