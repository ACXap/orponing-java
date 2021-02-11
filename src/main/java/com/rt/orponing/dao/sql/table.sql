-- Drop table
-- DROP TABLE public.nsi_temp_orponing;

CREATE TABLE public.nsi_temp_orponing (
	id serial NOT NULL, -- Идентификатор записи
	id_session int4 NOT NULL, -- Идентификатор сессии
	address varchar NOT NULL, -- Адрес для орпонизации - получения ГИД ОРПОН
	global_id varchar NULL, -- ГИД ОРПОН - глобальный идентификатор адреса в системе ОРПОН
	address_orpon varchar NULL, -- Адрес в системе ОРПОН
	parsing_level_code varchar NULL, -- Уровень разбора адреса
	unparsed_parts varchar NULL, -- Неразобранные части адреса
	quality_сode varchar NULL, -- Код качества переданной строки
	check_status varchar NULL, -- Статус автоматической проверки адреса
	date_create timestamp(0) NULL DEFAULT now(), -- Дата создания записи
	date_update timestamp(0) NULL, -- Дата внесения информации
	error varchar NULL, -- Ошибки при разборе адреса
	CONSTRAINT nsi_temp_orponing_input_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.nsi_temp_orponing IS 'Таблица адресов для орпонизации(получения ГИД ОРПОН)';

-- Column comments

COMMENT ON COLUMN public.nsi_temp_orponing.id IS 'Идентификатор записи';
COMMENT ON COLUMN public.nsi_temp_orponing.id_session IS 'Идентификатор сессии';
COMMENT ON COLUMN public.nsi_temp_orponing.address IS 'Адрес для орпонизации - получения ГИД ОРПОН';
COMMENT ON COLUMN public.nsi_temp_orponing.global_id IS 'ГИД ОРПОН - глобальный идентификатор адреса в системе ОРПОН';
COMMENT ON COLUMN public.nsi_temp_orponing.address_orpon IS 'Адрес в системе ОРПОН';
COMMENT ON COLUMN public.nsi_temp_orponing.parsing_level_code IS 'Уровень разбора адреса';
COMMENT ON COLUMN public.nsi_temp_orponing.unparsed_parts IS 'Неразобранные части адреса';
COMMENT ON COLUMN public.nsi_temp_orponing.quality_сode IS 'Код качества переданной строки';
COMMENT ON COLUMN public.nsi_temp_orponing.check_status IS 'Статус автоматической проверки адреса';
COMMENT ON COLUMN public.nsi_temp_orponing.date_create IS 'Дата создания записи';
COMMENT ON COLUMN public.nsi_temp_orponing.date_update IS 'Дата внесения информации';
COMMENT ON COLUMN public.nsi_temp_orponing.error IS 'Ошибки при разборе адреса';


-- Drop table
-- DROP TABLE public.nsi_temp_orponing_spr;

CREATE TABLE public.nsi_temp_orponing_spr (
	id int4 NOT NULL, -- Идентификатор записи
	code varchar NOT NULL, -- Код статуса
	"type" varchar NOT NULL, -- Тип статуса
	description varchar NOT NULL, -- Описание кода
	CONSTRAINT nsi_temp_orponing_spr_pk PRIMARY KEY (id),
	CONSTRAINT nsi_temp_orponing_spr_un UNIQUE (code)
);
COMMENT ON TABLE public.nsi_temp_orponing_spr IS 'Таблица - справочник описаний статусов орпонизации';

-- Column comments

COMMENT ON COLUMN public.nsi_temp_orponing_spr.id IS 'Идентификатор записи';
COMMENT ON COLUMN public.nsi_temp_orponing_spr.code IS 'Код статуса';
COMMENT ON COLUMN public.nsi_temp_orponing_spr."type" IS 'Тип статуса';
COMMENT ON COLUMN public.nsi_temp_orponing_spr.description IS 'Описание кода';