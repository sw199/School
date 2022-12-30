#!/bin/bash

#-------------------------------------------------------------------------------------------------#
# Scott Wedge                                                                                     #
#                                                                                                 #
#       This bash script is operated in the same nature as a normal command. Each of the          #
#        command switches pull various information from the files within /proc/.                  #
#                                                                                                 #
#-------------------------------------------------------------------------------------------------#

#defined variables
cmdUsage="usage: $0"
helpSwitch="-h    help menu"
userProcSwitch="-u    processes associated to specific user"
procDateSwitch="-d    process dates"
usedMemSwitch="-m    system memory used"
allMemSwitch="-M    all sys memory info (/proc/meminfo)"
topTenSwitch="-t    display top 10 mem and cpu hogs"
cpuInfoSwitch="-c    display information about system cpu"
sysVerSwitch="-s    displays system version information"
threadSwitch="-z    display top 25 processes with the most active threads (NLWP)"
badSwitch="command switch option is not recognised, here is some help:"


# function helpMenu()
#
# this function prints the help menu for the user
#
function helpMenu() {
  
  echo ""  
  echo "$cmdUsage"
  echo "$helpSwitch"
  echo "$userProcSwitch"
  echo "$procDateSwitch"
  echo "$usedMemSwitch"
  echo "$allMemSwitch"
  echo "$topTenSwitch"
  echo "$cpuInfoSwitch"
  echo "$sysVerSwitch"
  echo "$threadSwitch"
  echo ""
  echo "Examples for command options that require arguments:"
  echo "-u: ./proc_info -u USERNAME"
}


# checks input for if $1 is null or empty string
if [ $# -eq 0 ] || [ -z "$1" ]
then
    echo ""
    echo "Missing command switch/argument, here's some help:"
    echo ""
    helpMenu
fi


# function memInfo() 
#
# this function outputs the contents of /proc/meminfo
#
function memInfo() {

  read -p "Enter the block size you want to view output in (kB, MB, GB): " SIZES

  if [ $SIZES == 'kB' ]
  then
      echo ""
      cat /proc/meminfo | column -t

  elif [ $SIZES == 'MB' ]
  then
      echo ""
      awk '$3=="kB"{$2=$2/1024;$3="MB"} 1' /proc/meminfo | column -t
      
  elif [ $SIZES == 'GB' ]
  then
      echo ""
      awk '$3=="kB"{$2=$2/1024^2;$3="GB";} 1' /proc/meminfo | column -t

  else
      echo ""
      echo "Input can only be kB, MB, or GB."
      echo "Here's help:"
      echo ""
      helpMenu 
  fi
}


# function cpuCheck
#
# displays cpu info retrieved from /proc/cpuinfo
#
function cpuCheck() {
  
  # retrieves the number of cpu's installed on system
  cpuCount=$(grep -o "processor" /proc/cpuinfo | wc -l)
  echo ""
  echo "This system has "$cpuCount" processors installed."
  echo ""

  # parses cpu information from /proc/cpuinfo
  # sort -u == ignores duplicates
  grep "vendor_id" /proc/cpuinfo | sort -u
  grep "model name" /proc/cpuinfo |sort -u
  grep "cpu MHz" /proc/cpuinfo | sort -u  #cpu speed
  grep "address sizes" /proc/cpuinfo | sort -u
  grep "cache size" /proc/cpuinfo | sort -u
  grep "bugs" /proc/cpuinfo | sort -u
}


# function countThreads()
#
# displays active threads, and allows user to view threads per PID
#
function countThreads() {

  echo ""
  # nlwp == thread count
  threadC=$(ps -eo uid,pid,ppid,lwp,nlwp,time --sort=-nlwp | head -n 25 | column -t)
  echo "$threadC"
  echo ""

  read -p "Would you like to view the threads from a specific PID? (y/n): " YESNO
      
  if [ "$YESNO" == 'y' ]
    then
        echo ""
        read -p "Enter the PID to inspect: " URPID
        echo ""
        ls -l /proc/"$URPID"/task/
    else
        echo "Exiting."
        exit 0
  fi
}


# variables to be used within the below case statements
userProc=""  # -u
procDate=""  # -d
usedMem=""   # -m
allMem=""    # -M
topTen=""    # -t
cpuInfo=""   # -c
sysVer=""    # -s
threads=""   # -z

# this while loop controls the program flow depending on what switch the user uses.
while getopts "hu:dtmMcsz" cmdSwitch; do
  case "$cmdSwitch" in
    u)
      # outputs all PID's associated to a specific user
      
      # $2 == username, -u == RUID, -U == EUID
      USER=$(ps -U "$2" -u "$2" u)
      echo "$USER"
      ;;

    d)
      # outputs the date of process initialization
      ps -eo pid,lstart
      ;;

    m)
      # system memory in use (gibi):
      echo ""
      free -ht
      ;;

    M)
      # contents of /proc/meminfo converted to users choice of output
      memInfo
      ;;

    t)
      # displays the top ten resource hogs of mem and cpu
      ps -eo pid,ppid,cmd,%mem --sort=-%mem | head
      echo ""
      ps -eo pid,ppid,cmd,%cpu --sort=-%cpu | head
      ;;

    c)
      # pulls key information from /proc/cpuinfo
      cpuCheck
      ;;

    s)
      # pulls system version information from /proc/version
      echo ""
      cat /proc/version
      ;;

    z)
      # displays 25 processes with the most active threads
      countThreads
      ;;

    h)
      # help / usage output
      helpMenu 
      ;;

    ?)
      # if supplied command option does not exist
      echo ""
      echo "$badSwitch"
      echo ""
      helpMenu
      ;;

  esac
done
 

