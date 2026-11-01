-- An earlier build created usuario rows at login, before the profile existed.
-- Registration now creates usuario and perfil together, so drop the incomplete
-- accounts; their owners are sent to the profile form on their next visit.
DELETE FROM usuario WHERE id NOT IN (SELECT usuario_id FROM perfil);
