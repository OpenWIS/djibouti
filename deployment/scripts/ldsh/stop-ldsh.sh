#!/bin/bash
echo - ------------------------------------------------------------------------
echo - Stopping LDSH
echo - ------------------------------------------------------------------------
echo

cd stop && \
    ./stop-ldsh-mysql.sh && \
    ./stop-ldsh-ftpd.sh && \
    ./stop-ldsh-karaf.sh &&

echo - LDSH stopped