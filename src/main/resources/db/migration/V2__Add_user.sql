insert into user(id, active, username, password)
values (1, true, 'admin', '$2a$08$rbxYxDxANzv53jClMmL6JuQsJAzAGd6pHxsnI9RBIqmVeholrNP02');
insert into user_role(user_id, roles)
values (1, 'USER'), (1, 'ADMIN');