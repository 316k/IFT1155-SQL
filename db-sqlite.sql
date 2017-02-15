DROP TABLE IF EXISTS `locaux`;

CREATE TABLE `locaux` (
  `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `numero` TEXT NOT NULL,
  `categorie` TEXT NOT NULL,
  CHECK(`categorie` in ('reunion', 'cours'))
);

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `login` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `name` TEXT NOT NULL,
  `access_level` TEXT NOT NULL, -- "admin" ou "user"
  `last_login` INTEGER DEFAULT NULL, -- date du
  CHECK(`access_level` in ('admin', 'user'))
);
CREATE UNIQUE INDEX `unique_login` ON `users`(`login`);

--
-- Table structure for table `reservations`
--
DROP TABLE IF EXISTS `reservations`;

CREATE TABLE `reservations` (
  `_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `local_id` INTEGER NOT NULL, -- id du local réservé
  `user_id` INTEGER NOT NULL, -- id du user qui réserve
  `raison` TEXT NOT NULL, -- Raison invoquée -- penser à l'autocomplete pour éviter de retaper à chaque fois !
  `debut` INTEGER NOT NULL,
  `duration` INTEGER NOT NULL, -- Durée en secondes
  `cours` TEXT NOT NULL -- Sigle du cours pour lequel la réservation est faite (potentiellement vide)
);

--
-- Données de test
--
INSERT INTO `locaux`(`numero`, `categorie`) VALUES
       (2165, "reunion"),
       (2166, "reunion"),
       (2211, "reunion"),
       (2195, "reunion"),
       (3213, "reunion"),
       (2333, "cours"),
       (3181, "cours"),
       (3189, "cours"),
       (3248, "cours"),
       (3311, "cours");

INSERT INTO `users`(`login`, `password`, `name`, `access_level`) VALUES
       ("admin", "$up3rP4$$", "Jack Kerouac", "admin"),
       ("johnsmith", "superpass123", "John Smith", "user");


INSERT INTO `reservations`(`local_id`, `user_id`, `raison`, `debut`, `duration`, `cours`) VALUES
       (1, 2, "Réunion pour le MILA", 1487250000, 60 * 60, ""),
       (1, 2, "Réunion pour le MILA", 1487422800, 60 * 60, "");
