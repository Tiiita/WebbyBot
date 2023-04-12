CREATE TABLE IF NOT EXISTS settings (
    guildId CHAR(18) NOT NULL,
    primary key (guildId)
);

CREATE TABLE IF NOT EXISTS polls (
    guildId CHAR(18) NOT NULL,
    poll CHAR(64) NOT NULL,
    primary key (guildId)
);