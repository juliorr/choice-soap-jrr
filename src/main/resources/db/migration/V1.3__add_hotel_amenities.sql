create table hotel_amenities
(
    hotel_id     bigint not null,
    amenities_id bigint not null,
    primary key (hotel_id, amenities_id)
) engine = MyISAM;

create index FKsyruo3ecj16jhuscygipkxj5w
    on hotel_amenities (amenities_id);


INSERT INTO hotel_amenities (hotel_id, amenities_id)
values (1, 1),
       (2, 1),
       (2, 2);