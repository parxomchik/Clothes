#!/usr/bin/env bash
cd ./out/artifacts/clothes_war_exploded/
#tar -czf upload.tar * --exclude=resources --exclude=WEB-INF/dataSource.xml
tar -czf upload.tar * --exclude=WEB-INF/dataSource.xml

scp -rp upload.tar 55389e8e4382ec2187000041@zermon-cj5.rhcloud.com:app-root/dependencies/jbossews/webapps/ROOT/

rm upload.tar

ssh 55389e8e4382ec2187000041@zermon-cj5.rhcloud.com 'cd app-root/dependencies/jbossews/webapps/ROOT/ && tar -xzf upload.tar'

ssh 55389e8e4382ec2187000041@zermon-cj5.rhcloud.com 'echo 1 | gear restart'
#
#ssh 55389e8e4382ec2187000041@zermon-cj5.rhcloud.com 'tail_all'

rhc tail -a zermon