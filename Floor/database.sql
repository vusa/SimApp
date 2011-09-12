CREATE TABLE pathway
(
  "name" character varying(5) NOT NULL,
  startx integer,
  starty integer,
  endx integer,
  endy integer,
  joinatstart character varying(5),
  joinatend character varying(5),
  orientation character varying(25),
  CONSTRAINT pathway_pkey PRIMARY KEY (name)
);

CREATE TABLE person
(
  id integer NOT NULL,
  "name" character varying(25),
  dirx smallint NOT NULL DEFAULT 0,
  diry smallint NOT NULL DEFAULT 0,
  pathway character varying(5),
  lastx integer,
  lasty integer,
  CONSTRAINT person_pkey PRIMARY KEY (id)
);

--map
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v1', 26, 18, 26, 450, 'h1', 'h9', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v5', 154, 402, 154, 450, 'h8', 'h10', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h8', 90, 402, 411, 402, 'v3', 'v9', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v4', 154, 66, 154, 162, 'h2', 'h4', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h9', 26, 450, 90, 450, 'v1', 'v3', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v3', 90, 306, 90, 450, '', 'h9', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v2', 90, 18, 90, 66, 'h1', 'h2', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h10', 154, 450, 602, 450, 'v5', 'v11', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v7', 284, 306, 284, 450, 'h5', 'h10', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v6', 218, 162, 218, 306, 'h4', 'h5', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v9', 411, 162, 411, 450, 'h4', 'h10', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v8', 346, 306, 346, 402, 'h5', 'h8', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v11', 602, 18, 602, 450, 'h1', 'h10', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h5', 218, 306, 346, 306, 'v6', 'v8', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h4', 26, 162, 602, 162, 'v1', 'v11', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h7', 26, 354, 90, 354, 'v1', 'v3', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('v10', 476, 18, 476, 162, 'h1', 'h4', 'VERTICAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h6', 411, 306, 602, 306, 'v9', 'v11', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h1', 26, 18, 602, 18, 'v1', 'v11', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h3', 154, 114, 476, 114, 'v4', 'v10', 'HORIZONTAL');
INSERT INTO pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) VALUES ('h2', 90, 66, 476, 66, 'v2', 'v10', 'HORIZONTAL');


INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (0, 'John', 1, 0, 'h8', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (1, 'James', 0, -1, 'v1', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (2, 'Sipho', 0, -1, 'v6', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (3, 'Kevin', 0, 1, 'v6', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (4, 'Mark', -1, 0, 'h2', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (5, 'Dudu', 0, 1, 'v7', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (6, 'Dumi', 0, 1, 'v10', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (7, 'Gugu', -1, 0, 'h9', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (8, 'Thembi', 1, 0, 'h7', 0, 0);
INSERT INTO person (id, name, dirx, diry, pathway, lastx, lasty) VALUES (9, 'Dan', 1, 0, 'h4', 0, 0);

