-- Ensure the 'club' table exists
-- CREATE TABLE IF NOT EXISTS club
-- (
--     id              UUID PRIMARY KEY,
--     name            VARCHAR(255)        NOT NULL,
--     email           VARCHAR(255) UNIQUE NOT NULL,
--     address         VARCHAR(255)        NOT NULL,
--     date_of_birth   DATE                NOT NULL,
--     registered_date DATE                NOT NULL
--     );

-- Insert well-known UUIDs for specific clubs
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '81',
       'FC Barcelona',
       'Primera Division',
       '2014',
       'PD'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '81');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '86',
       'Real Madrid CF',
       'Primera Division',
       '2014',
       'PD'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '86');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '78',
       'Club Atlético de Madrid',
       'Primera Division',
       '2014',
       'PD'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '81');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '524',
       'Paris Saint-Germain FC',
       'Ligue 1',
       '2015',
       'FL1'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '524');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '5',
       'FC Bayern München',
       'Bundesliga',
       '2002',
       'BL1'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '5');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '108',
       'FC Internazionale Milano',
       'Serie A',
       '2019',
       'SA'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '108');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '109',
       'Juventus FC',
       'Serie A',
       '2019',
       'SA'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '109');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '113',
       'SSC Napoli',
       'Serie A',
       '2019',
       'SA'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '113');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '98',
       'AC Milan',
       'Serie A',
       '2019',
       'SA'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '98');

INSERT INTO club (id, name,league,league_id,league_code)
SELECT '57',
       'Arsenal FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '57');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '67',
       'Newcastle United FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '67');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '65',
       'Manchester City FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '65');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '64',
       'Liverpool FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '64');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '61',
       'Chelsea FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '61');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '66',
       'Manchester United FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '66');
INSERT INTO club (id, name,league,league_id,league_code)
SELECT '73',
       'Tottenham Hotspur FC',
       'Premier League',
       '2021',
       'PL'
    WHERE NOT EXISTS (SELECT 1
                  FROM club
                  WHERE id = '73');

