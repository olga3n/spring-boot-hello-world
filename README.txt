
#### PostgreSQL

sudo systemctl start postgresql (sudo service postgresql start)
sudo -u postgres psql 

create user phonebook_admin with password 'phonebook_admin_password';
grant all privileges on database phonebook_db to phonebook_admin;
\q

#### Build & Run

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk/ 

mvn package (mvn test)

mvn spring-boot:run  
