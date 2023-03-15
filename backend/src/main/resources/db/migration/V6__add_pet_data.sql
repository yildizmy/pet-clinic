INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (1, 'Lassie', 1, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (2, 'Kitty', 2, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (3, 'Tweety', 3, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (4, 'Charlie', 1, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (5, 'Lucy', 2, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (6, 'Angel', 3, 1);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (7, 'Odie', 1, 2);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (8, 'Garfield', 2, 2);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (9, 'Jack', 3, 2);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (10, 'Tom', 2, 3);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (11, 'Felix', 2, 3);
INSERT INTO public.pet (id, "name", type_id, user_id) VALUES (12, 'Spike', 3, 3);

SELECT setval('sequence_pet', max(id)) FROM public."pet";