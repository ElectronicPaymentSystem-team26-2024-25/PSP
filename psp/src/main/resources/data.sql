INSERT INTO public.users (email, password, type) VALUES ('johndoe@gmail.com', '$2a$10$4lCe3JbtoCfpntOtCDiOEuLsAO1hEUZX4uC5hJutlVy86W5ME32ai', 1);
INSERT INTO public.users (email, password, type) VALUES ('administrator@sep.uns.rs', '$2a$10$.dgV8KSs0kBwYpNbS6KZf.GonlAZ1X50SXQm7RVJNtbTHL/8svA.S', 0);

INSERT INTO public.merchants (merchant_id, merchant_password, business_name, business_email, legal_name, legal_lastname) VALUES ('123e4567-e89b-12d3-a456-426614174000', '789e1234-e89b-56d3-a456-426614174111', 'Tech Solutions', 'johndoe@gmail.com', 'John', 'Doe');

INSERT INTO public.payment_subscriptions (merchant_id, payment_method_id, is_active) VALUES (1, 1, true);
INSERT INTO public.payment_subscriptions (merchant_id, payment_method_id, is_active) VALUES (1, 2, true);
INSERT INTO public.payment_subscriptions (merchant_id, payment_method_id, is_active) VALUES (1, 3, false);

INSERT INTO public.merchant_bank (ID, MERCHANT_ID, MERCHANT_PASSWORD, PORT, BANK_NAME)
VALUES (1, '123e4567-e89b-12d3-a456-426614174000', '789e1234-e89b-56d3-a456-426614174111', '8060', 'Banka 1');

INSERT INTO public.payment_methods (id, name, type, address, endpoint) VALUES (1, 'PayPal', 1, 'paypal-service', '/api/paypal');
INSERT INTO public.payment_methods (id, name, type, address, endpoint) VALUES (2, 'Bank', 0, 'bank.local', '/api/bank');
INSERT INTO public.payment_methods (id, name, type, address, endpoint) VALUES (3, 'Crypto', 2, 'cryto-service', '/api/crypto');

