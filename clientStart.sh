#!/bin/bash

# Give 2 port number on command line to run
if [ "$#" -ne 2 ] && [ "$#" -ne 4 ]; then
  echo "usage: clientStart <port1> <port2> [<port3> <port4>]"
  exit 1
fi

# Make sure you actually type in numbers
re='^[0-9]+$'
if ! [[ $1 =~ $re ]] || ! [[ $2 =~ $re ]] ; then
  echo "Error: non-integer value found"
  exit 1
fi

# Check 3rd and 4th argument if exists
if [ "$#" -eq 4 ]; then
  if ! [[ $3 =~ $re ]] || ! [[ $4 =~ $re ]] ; then
    echo "Error: non-integer value found"
    exit 1
  fi
fi

# Keep port numbers in proper range
if [ "$1" -le 1024 ] || [ "$2" -le 1024 ]; then
  echo "Error: Port numbers must be above 1024"
  exit 1
fi

# Check 3rd and 4th
if [ "$#" -eq 4 ]; then
  if [ "$3" -le 1024 ] || [ "$4" -le 1024 ]; then
    echo "Error: Port numbers must be above 1024"
    exit 1
  fi
fi

# Cannot use same port number for both servers
if [ "$1" -eq "$2" ]; then
  echo "Error: same port number detected"
  exit 1
fi

# Run with 2 players
if [ "$#" -eq 2 ]; then
  java -cp build/libs/Quoridor.jar GameClient localhost:$1 localhost:$2
fi

# Run with 4 players
if [ "$#" -eq 4 ]; then
  java -cp build/libs/Quoridor.jar GameClient localhost:$1 localhost:$2 localhost:$3 localhost:$4
fi
