--
-- Функции для базы где будут адреса для орпонизации
--

CREATE OR REPLACE PROCEDURE public.nsi_temp_orponing_update_output_data(id_p integer, global_id_p bigint, address_opron_p character varying, parsing_level_code_p character varying, unparsed_parts_p character varying, "quality_сode_p" character varying, check_status_p character varying, error_p character varying)
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $procedure$
	BEGIN
		update public.nsi_temp_orponing set global_id = global_id_p,
													address_orpon = address_opron_p,
													parsing_level_code = parsing_level_code_p,
													unparsed_parts = unparsed_parts_p,
													quality_сode = quality_сode_p,
													check_status = check_status_p,
													error = error_p,
													date_update = now()
													where id = id_p;
	END;
$procedure$;

CREATE OR REPLACE FUNCTION public.nsi_temp_orponing_test_bd()
 RETURNS integer
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
	BEGIN
		perform 'public.nsi_temp_orponing'::regclass;
		return 1;
	END;
$function$;

CREATE OR REPLACE FUNCTION public.nsi_temp_orponing_select_input_data()
 RETURNS TABLE(id integer, address character varying)
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
	BEGIN
		return query select I.id, I.address
					from public.nsi_temp_orponing I
						where I.global_id is null and I.check_status is null and I.error is null;
	END;
$function$;

--
-- Функции для базы со справочником ГИД - Адрес
--

CREATE OR REPLACE FUNCTION public.nsi_get_address_by_global_id(globalid_p bigint)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
address text;
	begin

	select H.adr_adm_ter into address from public.ent_as_house H where H.livestatus = 1 and H.orponid = globalid_p;
	if(address is null) then
		select A.adr_adm_ter into address from public.ent_as_addrobj A where A.livestatus = 1 and A.orponid = globalid_p;
	end if;

	return address;
	END;
$function$;

CREATE OR REPLACE FUNCTION public.nsi_get_address_by_global_ids(list bigint[])
 RETURNS TABLE("ГИД" bigint, "Адрес" text)
 LANGUAGE plpgsql
AS $function$
	begin
		return query select H.orponid, H.adr_adm_ter from ent_as_house H where H.livestatus = 1 and H.orponid = ANY (list);
		return query select A.orponid, A.adr_adm_ter from ent_as_addrobj A where A.livestatus = 1 and A.orponid = ANY (list);
	END;
$function$;