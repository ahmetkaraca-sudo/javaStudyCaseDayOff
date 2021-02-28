insert into granted_authorities(role) values ('ROLE_USER');
insert into granted_authorities(role) values ('ROLE_MANAGER');


-- User
insert into user (identity_id,username ,name ,surname, start_day_ofwork , password, granted_authorities_id) values ('317','seyit','seyit', 'karaca',  '2020-04-04','$2a$10$kGaDr.oyCBc8W5cF3/uBXei8BjMc6uDM2CUP/XyG7.4gPYrrmpzfe', 1);
insert into user (identity_id,username ,name ,surname, start_day_ofwork , password, granted_authorities_id) values ('318','ahmet','seyit', 'karaca',  '2020-04-04','$2a$10$kGaDr.oyCBc8W5cF3/uBXei8BjMc6uDM2CUP/XyG7.4gPYrrmpzfe', 2);







--  2020
insert into public_holiday( date, duration) values ('2020-01-01' , 1.0);
insert into public_holiday( date, duration) values ('2020-04-23' , 1.0);
insert into public_holiday( date, duration) values ('2020-05-01' , 1.0);
insert into public_holiday( date, duration) values ('2020-05-19' , 1.0);
insert into public_holiday( date, duration) values ('2020-05-23' , 0.5);
insert into public_holiday( date, duration) values ('2020-05-24' , 1.0);
insert into public_holiday( date, duration) values ('2020-05-25' , 1.0);
insert into public_holiday( date, duration) values ('2020-05-26' , 1.0);
insert into public_holiday( date, duration) values ('2020-07-15' , 1.0);
insert into public_holiday( date, duration) values ('2020-07-30' , 0.5);
insert into public_holiday( date, duration) values ('2020-07-31' , 1.0);
insert into public_holiday( date, duration) values ('2020-08-01' , 1.0);
insert into public_holiday( date, duration) values ('2020-08-02' , 1.0);
insert into public_holiday( date, duration) values ('2020-08-03' , 1.0);
insert into public_holiday( date, duration) values ('2020-08-30' , 1.0);
insert into public_holiday( date, duration) values ('2020-10-28' , 0.5);
insert into public_holiday( date, duration) values ('2020-10-29' , 1.0);

--  2021
insert into public_holiday( date, duration) values ('2021-01-01' , 1.0);
insert into public_holiday( date, duration) values ('2021-04-23' , 1.0);
insert into public_holiday( date, duration) values ('2021-05-01' , 1.0);
insert into public_holiday( date, duration) values ('2021-05-12' , 0.5);
insert into public_holiday( date, duration) values ('2021-05-13' , 1.0);
insert into public_holiday( date, duration) values ('2021-05-14' , 1.0);
insert into public_holiday( date, duration) values ('2021-05-15' , 1.0);
insert into public_holiday( date, duration) values ('2021-05-19' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-15' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-19' , 0.5);
insert into public_holiday( date, duration) values ('2021-07-20' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-21' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-22' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-23' , 1.0);
insert into public_holiday( date, duration) values ('2021-07-30' , 1.0);
insert into public_holiday( date, duration) values ('2021-10-28' , 0.5);
insert into public_holiday( date, duration) values ('2021-10-29' , 1.0);

