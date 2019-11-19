--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1
-- Dumped by pg_dump version 11.1

-- Started on 2019-11-19 15:57:12

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2867 (class 1262 OID 42425)
-- Name: softservetimetable; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE softservetimetable WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';


ALTER DATABASE softservetimetable OWNER TO postgres;

\connect softservetimetable

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 49485)
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groups (
    id integer NOT NULL,
    name character varying(16) NOT NULL
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 49483)
-- Name: groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.groups_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groups_id_seq OWNER TO postgres;

--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 196
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.groups_id_seq OWNED BY public.groups.id;


--
-- TOC entry 203 (class 1259 OID 49692)
-- Name: lessons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lessons (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    teacherid integer NOT NULL,
    number character varying NOT NULL,
    groupid integer NOT NULL,
    roomid integer NOT NULL,
    day character varying NOT NULL
);


ALTER TABLE public.lessons OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 49690)
-- Name: lessons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lessons_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lessons_id_seq OWNER TO postgres;

--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 202
-- Name: lessons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lessons_id_seq OWNED BY public.lessons.id;


--
-- TOC entry 199 (class 1259 OID 49495)
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rooms (
    id integer NOT NULL,
    name character varying(16) NOT NULL,
    type character varying NOT NULL
);


ALTER TABLE public.rooms OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 49493)
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rooms_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rooms_id_seq OWNER TO postgres;

--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 198
-- Name: rooms_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.rooms_id_seq OWNED BY public.rooms.id;


--
-- TOC entry 201 (class 1259 OID 49508)
-- Name: teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teachers (
    id integer NOT NULL,
    fullname character varying(48) NOT NULL,
    type character varying NOT NULL
);


ALTER TABLE public.teachers OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 49506)
-- Name: teachers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.teachers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teachers_id_seq OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 200
-- Name: teachers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.teachers_id_seq OWNED BY public.teachers.id;


--
-- TOC entry 2706 (class 2604 OID 49488)
-- Name: groups id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups ALTER COLUMN id SET DEFAULT nextval('public.groups_id_seq'::regclass);


--
-- TOC entry 2709 (class 2604 OID 49695)
-- Name: lessons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons ALTER COLUMN id SET DEFAULT nextval('public.lessons_id_seq'::regclass);


--
-- TOC entry 2707 (class 2604 OID 49498)
-- Name: rooms id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms ALTER COLUMN id SET DEFAULT nextval('public.rooms_id_seq'::regclass);


--
-- TOC entry 2708 (class 2604 OID 49511)
-- Name: teachers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers ALTER COLUMN id SET DEFAULT nextval('public.teachers_id_seq'::regclass);


--
-- TOC entry 2855 (class 0 OID 49485)
-- Dependencies: 197
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.groups (id, name) VALUES (5, '501');
INSERT INTO public.groups (id, name) VALUES (6, '502');
INSERT INTO public.groups (id, name) VALUES (7, '503');
INSERT INTO public.groups (id, name) VALUES (8, '504');
INSERT INTO public.groups (id, name) VALUES (9, '505');
INSERT INTO public.groups (id, name) VALUES (10, '506');
INSERT INTO public.groups (id, name) VALUES (11, '507');
INSERT INTO public.groups (id, name) VALUES (12, '508');
INSERT INTO public.groups (id, name) VALUES (13, '509');
INSERT INTO public.groups (id, name) VALUES (14, '500');


--
-- TOC entry 2861 (class 0 OID 49692)
-- Dependencies: 203
-- Data for Name: lessons; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (21, 'Software engineering', 11, 'THIRD', 6, 25, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (19, 'Communications technology', 9, 'FIRST', 6, 23, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (20, 'Data management and analysis', 10, 'SECOND', 6, 24, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (22, 'Software engineering', 11, 'FOURTH', 6, 25, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (16, 'Java programming', 8, 'THIRD', 6, 20, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (15, 'Data structures', 7, 'SECOND', 6, 20, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (25, 'Java programming', 11, 'THIRD', 7, 21, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (26, 'Data structures', 10, 'SECOND', 7, 21, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (27, 'Linux essentials', 9, 'FIRST', 7, 21, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (28, 'Software engineering', 12, 'FOURTH', 8, 26, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (29, 'Data management and analysis', 12, 'THIRD', 8, 26, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (30, 'Software engineering', 12, 'FIFTH', 8, 26, 'MONDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (31, 'Java programming', 14, 'THIRD', 7, 26, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (32, 'Java programming', 14, 'FOURTH', 7, 26, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (33, 'Web technologies', 14, 'FIFTH', 7, 25, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (34, 'Communications technology', 16, 'FIRST', 8, 26, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (35, 'Linux essentials', 9, 'THIRD', 8, 20, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (36, 'Data management and analysis', 12, 'FOURTH', 8, 24, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (37, 'Data management and analysis', 12, 'FIRST', 7, 24, 'TUESDAY');
INSERT INTO public.lessons (id, name, teacherid, number, groupid, roomid, day) VALUES (14, 'Linux essentials', 6, 'FIRST', 6, 20, 'MONDAY');


--
-- TOC entry 2857 (class 0 OID 49495)
-- Dependencies: 199
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.rooms (id, name, type) VALUES (19, '100', 'AUDITORY');
INSERT INTO public.rooms (id, name, type) VALUES (20, '101', 'LABORATORY');
INSERT INTO public.rooms (id, name, type) VALUES (21, '102', 'LABORATORY');
INSERT INTO public.rooms (id, name, type) VALUES (22, '103', 'AUDITORY');
INSERT INTO public.rooms (id, name, type) VALUES (23, '104', 'AUDITORY');
INSERT INTO public.rooms (id, name, type) VALUES (24, '105', 'LABORATORY');
INSERT INTO public.rooms (id, name, type) VALUES (25, '106', 'AUDITORY');
INSERT INTO public.rooms (id, name, type) VALUES (26, '107', 'LABORATORY');
INSERT INTO public.rooms (id, name, type) VALUES (27, '108', 'AUDITORY');
INSERT INTO public.rooms (id, name, type) VALUES (28, '109', 'AUDITORY');


--
-- TOC entry 2859 (class 0 OID 49508)
-- Dependencies: 201
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.teachers (id, fullname, type) VALUES (7, 'Jayne Middleditch', 'POST_GRADUATE');
INSERT INTO public.teachers (id, fullname, type) VALUES (12, 'Linnie Gavrieli', 'POST_GRADUATE');
INSERT INTO public.teachers (id, fullname, type) VALUES (15, 'Osbourn Nyles', 'POST_GRADUATE');
INSERT INTO public.teachers (id, fullname, type) VALUES (17, 'Ambur Barbisch', 'POST_GRADUATE');
INSERT INTO public.teachers (id, fullname, type) VALUES (13, 'Ash Brettle', 'DOCENT');
INSERT INTO public.teachers (id, fullname, type) VALUES (18, 'Berry Zambon', 'DOCENT');
INSERT INTO public.teachers (id, fullname, type) VALUES (20, 'Carilyn Ingrey', 'DOCENT');
INSERT INTO public.teachers (id, fullname, type) VALUES (19, 'Collin Bland', 'PROFESSOR');
INSERT INTO public.teachers (id, fullname, type) VALUES (9, 'Giles Astle', 'PROFESSOR');
INSERT INTO public.teachers (id, fullname, type) VALUES (8, 'Goldie Hair', 'PROFESSOR');
INSERT INTO public.teachers (id, fullname, type) VALUES (6, 'Lynn Checketts', 'DOCENT');
INSERT INTO public.teachers (id, fullname, type) VALUES (11, 'Magnum Baumford', 'PROFESSOR');
INSERT INTO public.teachers (id, fullname, type) VALUES (16, 'Pooh Timlin', 'DOCENT');
INSERT INTO public.teachers (id, fullname, type) VALUES (14, 'Rodi De Laci', 'PROFESSOR');
INSERT INTO public.teachers (id, fullname, type) VALUES (10, 'Skip Groveham', 'PROFESSOR');


--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 196
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groups_id_seq', 14, true);


--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 202
-- Name: lessons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lessons_id_seq', 30, true);


--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 198
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rooms_id_seq', 28, true);


--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 200
-- Name: teachers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.teachers_id_seq', 20, true);


--
-- TOC entry 2723 (class 2606 OID 49704)
-- Name: lessons groupbusy; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT groupbusy UNIQUE (number, day, groupid);


--
-- TOC entry 2711 (class 2606 OID 49492)
-- Name: groups groups_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_name_key UNIQUE (name);


--
-- TOC entry 2713 (class 2606 OID 49490)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2725 (class 2606 OID 49700)
-- Name: lessons lessons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_pkey PRIMARY KEY (id);


--
-- TOC entry 2727 (class 2606 OID 49706)
-- Name: lessons roombusy; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT roombusy UNIQUE (number, day, roomid);


--
-- TOC entry 2715 (class 2606 OID 49505)
-- Name: rooms rooms_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_name_key UNIQUE (name);


--
-- TOC entry 2717 (class 2606 OID 49503)
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- TOC entry 2729 (class 2606 OID 49702)
-- Name: lessons teacherbusy; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT teacherbusy UNIQUE (number, day, teacherid);


--
-- TOC entry 2719 (class 2606 OID 49518)
-- Name: teachers teachers_fullname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_fullname_key UNIQUE (fullname);


--
-- TOC entry 2721 (class 2606 OID 49516)
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);


--
-- TOC entry 2731 (class 2606 OID 49712)
-- Name: lessons lessons_groupid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_groupid_fkey FOREIGN KEY (groupid) REFERENCES public.groups(id);


--
-- TOC entry 2732 (class 2606 OID 49717)
-- Name: lessons lessons_roomid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_roomid_fkey FOREIGN KEY (roomid) REFERENCES public.rooms(id);


--
-- TOC entry 2730 (class 2606 OID 49707)
-- Name: lessons lessons_teacherid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_teacherid_fkey FOREIGN KEY (teacherid) REFERENCES public.teachers(id);


-- Completed on 2019-11-19 15:57:13

--
-- PostgreSQL database dump complete
--

