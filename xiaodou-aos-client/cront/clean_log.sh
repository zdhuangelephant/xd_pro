#!/usr/bin/env bash
`find /xiaodou-aos-client/logs -ctime +7 | xargs -n 1 rm -rf`
`find /xiaodou-aos-client/logs -size +100000000c | xargs -n 1 rm -rf`