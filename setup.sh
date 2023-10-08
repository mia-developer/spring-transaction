docker-compose down
docker-compose up -d

while [ "$(docker inspect -f '{{.State.Health.Status}}' ro-mysql)" != "healthy" ]; do
  sleep 1
  echo 'Waiting ...'
done
echo 'Done'

# This script was adapted from the following source:
# https://github.com/YouJaeBeom/DB_Master-Slave_dockercompose/blob/main/run.sh
master_log_file=$(mysql -h127.0.0.1 --port 4407 -uroot -proot -e "show master status\G" | grep "File")
master_log_file=${master_log_file}
master_log_file=${master_log_file//[[:blank:]]/}
master_log_file=${master_log_file:5}

master_log_pos=$(mysql -h127.0.0.1 --port 4407  -uroot -proot -e "show master status\G" | grep "Position")
master_log_pos=${master_log_pos}
master_log_pos=${master_log_pos//[[:blank:]]/}
master_log_pos=${master_log_pos:9}

query="CHANGE MASTER TO MASTER_HOST='rw-mysql', MASTER_USER='root', MASTER_PASSWORD='root', MASTER_LOG_FILE='${master_log_file}', MASTER_LOG_POS=${master_log_pos}"
mysql --host 127.0.0.1 --port 4408 -uroot -proot -e "stop slave"
mysql --host 127.0.0.1 --port 4408 -uroot -proot -e "${query}"
mysql --host 127.0.0.1 --port 4408 -uroot -proot -e "start slave"

echo "$(mysql -h127.0.0.1 --port 4408 -uroot -proot -e 'show slave status\G')"
