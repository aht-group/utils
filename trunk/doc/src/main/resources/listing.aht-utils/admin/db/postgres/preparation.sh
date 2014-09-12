sudo mkdir /home/postgres
sudo touch /home/postgres/dump-@@@APP@@@.sh
sudo chmod +x /home/postgres/dump-@@@APP@@@.sh
sudp chown postgres.postgrs /home/postgres -R
sudp chmod og-rwx /home/postgres

sudo mkdir /home/dev/@@@APP@@@.dump
sudp chown dev.postgres /home/dev/@@@APP@@@-dump
sudp chmod ug+rwx /home/dev/@@@APP@@@.dump
sudp chmod o-rwx /home/dev/@@@APP@@@.dump