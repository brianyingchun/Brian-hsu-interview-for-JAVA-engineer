CREATE TABLE IF NOT EXISTS coindesk_coins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cname VARCHAR(255),
    chzcname VARCHAR(255)
);

insert into coindesk_coins (cname, chzcname) values ('USD', '美元');
insert into coindesk_coins (cname, chzcname) values ('GBP', '英鎊');
insert into coindesk_coins (cname, chzcname) values ('EUR', '歐元');
