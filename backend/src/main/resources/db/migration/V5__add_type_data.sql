INSERT INTO public."type" (id, "name", description) VALUES (1, 'Dog', null);
INSERT INTO public."type" (id, "name", description) VALUES (2, 'Cat', null);
INSERT INTO public."type" (id, "name", description) VALUES (3, 'Bird', null);

SELECT setval('sequence_type', max(id)) FROM public."type";