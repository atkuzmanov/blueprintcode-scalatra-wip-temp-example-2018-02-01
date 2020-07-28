#!/bin/sh
#
# blueprintcode service
#
# chkconfig:   35 99 99
# description: blueprintcode service

# Source function library.
. /etc/rc.d/init.d/functions

exec="java -jar /usr/lib/blueprintcode/blueprintcode.jar"
prog="blueprintcode"
pidfile="/var/run/blueprintcode/blueprintcode.pid"
output_logfile=/var/log/blueprintcode/output.log

[ -e /etc/sysconfig/$prog ] && . /etc/sysconfig/$prog

lockfile=/var/lock/subsys/$prog

start() {
    echo -n $"Starting $prog: "
    daemon --pidfile=${pidfile} $exec
    retval=$?
    echo
    [ $retval -eq 0 ] && touch $lockfile
    return $retval
}

stop() {
    echo -n $"Stopping $prog: "
    killproc -p $pidfile
    retval=$?
    echo
    [ $retval -eq 0 ] && rm -f $lockfile
    echo "Application stopped." >> $output_logfile
    return $retval
}

restart() {
    stop
    start
}

reload() {
    restart
}

force_reload() {
    restart
}

rh_status() {
    # run checks to determine if the service is running or use generic status
    status -p $pidfile $prog
}

rh_status_q() {
    rh_status >/dev/null 2>&1
}


case "$1" in
    start)
        rh_status_q && exit 0
        $1
        ;;
    stop)
        rh_status_q || exit 0
        $1
        ;;
    restart)
        $1
        ;;
    reload)
        rh_status_q || exit 7
        $1
        ;;
    force-reload)
        force_reload
        ;;
    status)
        rh_status
        ;;
    condrestart|try-restart)
        rh_status_q || exit 0
        restart
        ;;
    *)
        echo $"Usage: $0 {start|stop|status|restart|condrestart|try-restart|reload|force-reload}"
        exit 2
esac
exit $?
