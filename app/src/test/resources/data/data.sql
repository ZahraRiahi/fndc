insert into fndc.financial_document_description (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (51, 'اسناد قرارداد بیمه', 1, 1000, 1000, to_date('19-07-2021 15:30:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2021 12:59:38', 'dd-mm-yyyy hh24:mi:ss'), 2, null);

insert into fndc.financial_document_description (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (5001, 'اسناد تجهیزات', 1, 1000, 1000, to_date('21-09-2021 12:58:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2021 12:58:44', 'dd-mm-yyyy hh24:mi:ss'), 2, null);

insert into fndc.financial_document_description (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3156, 'خرید نقدی', 1, 1000, 1000, to_date('25-07-2021 13:46:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2021 13:46:14', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_description (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (2, 'دارایی های جاری', 1, 1000, 1000, to_date('18-07-2021', 'dd-mm-yyyy'), to_date('18-07-2021', 'dd-mm-yyyy'), 0, null);

insert into fndc.financial_document_description (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3008, 'پرداخت هزینه و خدمات', 1, 1000, 1000, to_date('25-07-2021 11:31:10', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2021 11:33:13', 'dd-mm-yyyy hh24:mi:ss'), 4, null);

------------------------------------------------------------------------------------------

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (1, 'سازمان / شعبه / دفتر / تاریخ / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$ORG$DEP$LEG$DAT$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (2, 'شعبه / دفتر / تاریخ / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$DEP$LEG$DAT$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (3, 'دفتر / تاریخ / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$LEG$DAT$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (4, 'تاریخ / سریال', 1000,1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$DAT$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (5, 'سازمان / دفتر / کد دوره مالی / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$ORG$LEG$PRI$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (6, 'دفتر / کد دوره مالی / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$LEG$PRI$SRL');

insert into fndc.financial_numbering_format_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (7, 'کد دوره مالی / سریال', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, '$PRI$SRL');

------------------------------------------------------------------------------------------

insert into fndc.financial_numbering_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, TYPE_STATUS) values (4, 'شعبه', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, 1);

insert into fndc.financial_numbering_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, TYPE_STATUS) values (1, 'موقت', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, 1);

insert into fndc.financial_numbering_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, TYPE_STATUS) values (2, 'عطف', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, 1);

insert into fndc.financial_numbering_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, TYPE_STATUS) values (3, 'دائمی', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 0, null, 3);

insert into fndc.financial_numbering_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, TYPE_STATUS) values (5, 'ماهانه',1000,1000, to_date('22-08-2021', 'dd-mm-yyyy'), to_date('22-08-2021', 'dd-mm-yyyy'), 0, null, 3);

-------------------------------------------------------------------------------

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (151, '200-H', 5, 1, 5, 1000, 1000, to_date('24-07-2021 16:30:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2021 14:58:08', 'dd-mm-yyyy hh24:mi:ss'), 5, to_date('25-07-2021 14:58:08', 'dd-mm-yyyy hh24:mi:ss'), '$DAT', 5, 10);

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (201, '300-F', 3, 1, 3,1000, 1000, to_date('24-07-2021 17:01:48', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-07-2021 11:00:41', 'dd-mm-yyyy hh24:mi:ss'), 6, to_date('28-07-2021 11:00:41', 'dd-mm-yyyy hh24:mi:ss'), '$DAT', 5, 20);

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (4001, null, 5, 1, 6, 1000, 1000, to_date('21-09-2021 15:24:07', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2021 15:24:24', 'dd-mm-yyyy hh24:mi:ss'), 2, to_date('21-09-2021 15:24:24', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (1001, null, 1, 1, 1, 1000, 1000, to_date('26-07-2021 09:59:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-07-2021 09:59:16', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '$ORG$DAT', 5, 30);

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (1002, null, 2, 1, 5, 1000, 1000, to_date('26-07-2021 10:23:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-07-2021 10:23:21', 'dd-mm-yyyy hh24:mi:ss'), 1, null, '$DAT', 5, 40);

insert into fndc.financial_numbering_format (ID, DESCRIPTION, FINANCIAL_NUMBERING_TYPE_ID, ORGANIZATION_ID, NUMBERING_FORMAT_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RESETER, SERIAL_LENGTH, FIRST_SERIAL) values (3001, null, 4,1, 1, 1000, 1000, to_date('12-09-2021 16:54:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-09-2021 16:54:38', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '$DAT', 5, 50);

----------------------------------------------------------------------------

insert into fndc.financial_system (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, 'سیستم مالی', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null);
-----------------------------------------------------------------------------

insert into fndc.financial_document_type (ID, DESCRIPTION, ACTIVE_FLAG, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_SYSTEM_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6, 'سند دریافت', 1, 0, 1, 1, 1000, 1000, to_date('31-07-2021 14:50:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 11:48:45', 'dd-mm-yyyy hh24:mi:ss'), 2, to_date('23-09-2021 11:48:45', 'dd-mm-yyyy hh24:mi:ss'));

insert into fndc.financial_document_type (ID, DESCRIPTION, ACTIVE_FLAG, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_SYSTEM_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (51, 'سند دریافت پرداخت 11111', 0, 0, 1, 1, 1000, 1000, to_date('31-07-2021 14:52:38', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2021 15:38:34', 'dd-mm-yyyy hh24:mi:ss'), 4,null);

insert into fndc.financial_document_type (ID, DESCRIPTION, ACTIVE_FLAG, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_SYSTEM_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (101, 'سند بستن حساب ', 1, 0, 1, 1, 1000, 1000, to_date('31-07-2021 15:08:56', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 11:48:49', 'dd-mm-yyyy hh24:mi:ss'), 2, null);

insert into fndc.financial_document_type (ID, DESCRIPTION, ACTIVE_FLAG, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_SYSTEM_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (151, 'سند حساب', 0, 0, 1, 1, 1000, 1000, to_date('31-07-2021 15:18:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 11:48:59', 'dd-mm-yyyy hh24:mi:ss'), 4, null);

-----------------------------------------------------------------------------
insert into fndc.financial_document_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, '10', 'ایجاد', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null);

insert into fndc.financial_document_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (2, '20', 'تائید کاربر', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null);

insert into fndc.financial_document_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3, '30', 'قطعی', 1000, 1000, to_date('29-09-2021', 'dd-mm-yyyy'), to_date('29-09-2021', 'dd-mm-yyyy'), 1, null);

--------------------------------------------------------------------------------------------------------

insert into fnac.financial_coding_type (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (4101, 'کدینگ شماره 444', 1, 1000, 1000, to_date('03-07-2021 13:19:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-07-2021 08:21:33', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

insert into fnac.financial_coding_type (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (7001, 'کدینگ شماره 2', 1, 1000, 1000, to_date('15-07-2021 08:44:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-10-2021 12:32:32', 'dd-mm-yyyy hh24:mi:ss'), 19, null);

insert into fnac.financial_coding_type (ID, DESCRIPTION, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (4102, 'کدینگ 1', 1, 1000,1000, to_date('04-07-2021 14:39:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-07-2021 14:42:49', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

---------------------------------------------------------------------------------

insert into fndc.financial_ledger_type (ID, DESCRIPTION, FINANCIAL_CODING_TYPE_ID, ORGANIZATION_ID, ACTIVE_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (10, 'دفتر اصلی', 4101, 1, 1,1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null, '21');

insert into fndc.financial_ledger_type (ID, DESCRIPTION, FINANCIAL_CODING_TYPE_ID, ORGANIZATION_ID, ACTIVE_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (2, 'دفتر مرکزی', 7001, 1, 1, 1000, 1000, to_date('02-08-2021', 'dd-mm-yyyy'), to_date('02-08-2021', 'dd-mm-yyyy'), 1, null, '01');

insert into fndc.financial_ledger_type (ID, DESCRIPTION, FINANCIAL_CODING_TYPE_ID, ORGANIZATION_ID, ACTIVE_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, CODE) values (5, 'دفتر اصلی', 4101, 1, 1, 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null, '21');


--------------------------------------------------------------------------------

insert into fndc.financial_department (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, ORGANIZATION_ID) values (1, '1', 'شعبه 1', 1000, 1000, to_date('20-06-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null, 1);

insert into fndc.financial_department (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, ORGANIZATION_ID) values (5, '5', 'واحد مالی 4', 1000, 1000, to_date('20-07-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null, 1);

insert into fndc.financial_department (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, ORGANIZATION_ID) values (4, '4', 'واحد مالی 3', 1000, 1000, to_date('20-10-2021', 'dd-mm-yyyy'), to_date('20-07-2021', 'dd-mm-yyyy'), 1, null, 1);

-------------------------------------------------------------------------------------------
insert into fnpr.financial_period_type (ID, DESCRIPTION, CALENDAR_TYPE_ID, FROM_MONTH, TO_MONTH, CURRENT_YEAR_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, 'سالیانه شمسی از مهر ماه جاری تا شهریور سال بعد', 1, 7, 6, 0,1000, 1000, to_date('01-05-2021', 'dd-mm-yyyy'), to_date('01-05-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_period_type (ID, DESCRIPTION, CALENDAR_TYPE_ID, FROM_MONTH, TO_MONTH, CURRENT_YEAR_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (2, 'سالیانه میلادی از ژانویه تا سپتامبر', 2, 1, 12, 1, 1000, 1000, to_date('02-05-2021', 'dd-mm-yyyy'), to_date('01-05-2021', 'dd-mm-yyyy'), 1, null);

----------------------------------------------------------------------
insert into fnpr.financial_period_type_assign (ID, FINANCIAL_PERIOD_TYPE_ID, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, ACTIVE_FLAG, START_DATE, DELETED_DATE) values (10027, 2, 1, 1000, 1000, to_date('02-10-2021 16:06:11', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-10-2021 14:06:46', 'dd-mm-yyyy hh24:mi:ss'), 1, 0, to_date('23-09-2021', 'dd-mm-yyyy'), null);

insert into fnpr.financial_period_type_assign (ID, FINANCIAL_PERIOD_TYPE_ID, ORGANIZATION_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, ACTIVE_FLAG, START_DATE, DELETED_DATE) values (8028, 2, 1, 1000, 1000, to_date('22-09-2021 08:42:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-09-2021 11:30:03', 'dd-mm-yyyy hh24:mi:ss'), 1, 0, to_date('21-03-2021', 'dd-mm-yyyy'), null);

--------------------------------------------------------------------

insert into fnpr.financial_period_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, 'OPEN', 'باز', 1000, 1000, to_date('03-05-2021', 'dd-mm-yyyy'), to_date('25-05-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_period_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (2, 'CLOSE', 'بسته', 1000, 1000, to_date('03-05-2021', 'dd-mm-yyyy'), to_date('25-05-2021', 'dd-mm-yyyy'), 1, null);

-------------------------------------------------------------------------
insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (13, 2, 'ژانویه', 1, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (14, 2, 'فوریه', 2, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (15, 2, 'مارس', 3, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (16, 2, 'آوریل', 4, 1000,1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (17, 2, 'می', 5, 1000,1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (18, 2, 'ژوئن', 6, 1000,1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (19, 2, 'جولای', 7, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (20, 2, 'آگوست', 8, 1000,1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (21, 2, 'سپتامبر', 9,1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (22, 2, 'اکتبر', 10, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (23, 2, 'نوامبر', 11, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

insert into fnpr.financial_month_type (ID, FINANCIAL_PERIOD_TYPE_ID, DESCRIPTION, MONTH_NUMBER, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (24, 2, 'دسامبر', 12, 1000, 1000, to_date('22-06-2021', 'dd-mm-yyyy'), to_date('22-06-2021', 'dd-mm-yyyy'), 1, null);

-----------------------------------------------------------------------------------

insert into fnpr.financial_month_status (ID, CODE, NAME, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE)
values (1, 'OPEN', 'باز', 1000,1000, to_date('03-05-2021', 'dd-mm-yyyy'), to_date('25-05-2021', 'dd-mm-yyyy'), 1, null);

-----------------------------------------------------------------

insert into fnpr.financial_period (ID, FINAN_PERIOD_TYPE_ASSIGN_ID, START_DATE, END_DATE, OPEN_MONTH_COUNT, FINANCIAL_PERIOD_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DESCRIPTION, CODE) values (13172, 10027, to_date('01-09-2026', 'dd-mm-yyyy'), to_date('31-08-2027', 'dd-mm-yyyy'), 1, 2, 1, 112, to_date('16-10-2021 09:09:23', 'dd-mm-yyyy hh24:mi:ss'), to_date('31-10-2021 13:46:53', 'dd-mm-yyyy hh24:mi:ss'), 5, null, 'دوره مالی منتهی به 1406/06/09', '11');

insert into fnpr.financial_period (ID, FINAN_PERIOD_TYPE_ASSIGN_ID, START_DATE, END_DATE, OPEN_MONTH_COUNT, FINANCIAL_PERIOD_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DESCRIPTION, CODE) values (10372, 8028, to_date('02-08-2024', 'dd-mm-yyyy'), to_date('01-08-2025', 'dd-mm-yyyy'), 5, 1, 1000, 1000, to_date('22-09-2021 14:40:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-09-2021 09:11:27', 'dd-mm-yyyy hh24:mi:ss'), 2, null, 'دوره مالی منتهی به 1404/05/10', '15');

------------------------------------------------------------------

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38090, 10372, 13, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-03-2021', 'dd-mm-yyyy'), to_date('01-04-2021', 'dd-mm-yyyy'), 'ژانویه 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38091, 10372, 14, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-04-2021', 'dd-mm-yyyy'), to_date('01-05-2021', 'dd-mm-yyyy'), 'فوریه 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38092, 10372, 15, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-05-2021', 'dd-mm-yyyy'), to_date('01-06-2021', 'dd-mm-yyyy'), 'مارس 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38093, 10372, 16, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-06-2021', 'dd-mm-yyyy'), to_date('01-07-2021', 'dd-mm-yyyy'), 'آوریل 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38094, 10372, 17, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-07-2021', 'dd-mm-yyyy'), to_date('01-08-2021', 'dd-mm-yyyy'), 'می 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38095, 10372, 18, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-08-2021', 'dd-mm-yyyy'), to_date('01-09-2021', 'dd-mm-yyyy'), 'ژوئن 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38096, 10372, 19, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-09-2021', 'dd-mm-yyyy'), to_date('01-10-2021', 'dd-mm-yyyy'), 'جولای 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38097, 10372, 20, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-10-2021', 'dd-mm-yyyy'), to_date('01-11-2021', 'dd-mm-yyyy'), 'آگوست 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38098, 10372, 21, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-11-2021', 'dd-mm-yyyy'), to_date('01-12-2021', 'dd-mm-yyyy'), 'سپتامبر 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38099, 10372, 22, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-12-2021', 'dd-mm-yyyy'), to_date('01-01-2022', 'dd-mm-yyyy'), 'اکتبر 2021', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38100, 10372, 23, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-01-2022', 'dd-mm-yyyy'), to_date('01-02-2022', 'dd-mm-yyyy'), 'نوامبر 2022', null);

insert into fnpr.financial_month (ID, FINANCIAL_PERIOD_ID, FINANCIAL_MONTH_TYPE_ID, FINANCIAL_MONTH_STATUS_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, START_DATE, END_DATE, DESCRIPTION, DELETED_DATE)
values (38101, 10372, 24, 1, 1000, 1000, to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:40:51', 'dd-mm-yyyy hh24:mi:ss'), 0, to_date('02-02-2022', 'dd-mm-yyyy'), to_date('01-03-2022', 'dd-mm-yyyy'), 'دسامبر 2022', null);

------------------------------------------------------


insert into fndc.ledger_numbering_type (ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_NUMBERING_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (4150, 10, 2, 1000, 1000, to_date('16-10-2021 10:32:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 10:32:25', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

-------------------------------------------------------------------

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6303, to_date('12-10-2021', 'dd-mm-yyyy'), 'خرید نقدی', 1, 0, 1, 151, 13172, 10, 1, 1000, 1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10211500060');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (8257, to_date('19-10-2021', 'dd-mm-yyyy'), 'ببب', 1, 0, 1, 151, 10372, 2, 5, 1000, 1000, to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011100044');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6304, to_date('12-10-2021', 'dd-mm-yyyy'), 'اسناد تجهیزات', 1, 0, 1, 151, 10372, 10, 1, 1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10211500061');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6305, to_date('12-10-2021', 'dd-mm-yyyy'), 'یسی', 1, 0, 1, 151, 13172, 10, 1, 1000, 1000, to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10211500062');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6306, to_date('12-10-2021', 'dd-mm-yyyy'), 'یسی', 1, 0, 1, 151, 13172, 10, 1, 1000, 1000, to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10211500063');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6307, to_date('12-10-2021', 'dd-mm-yyyy'), 'vvvv', 1, 0, 1, 101, 13172, 10, 1, 1000, 1000, to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2021 09:08:42', 'dd-mm-yyyy hh24:mi:ss'), 2, null, null, '10211500064');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (8256, to_date('19-10-2021', 'dd-mm-yyyy'), 'wwww', 1, 0, 1, 151, 13172, 2, 5, 1000, 1000, to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011100043');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (8258, to_date('19-10-2021', 'dd-mm-yyyy'), 'ققق', 1, 0, 1, 151, 13172, 2, 5, 1000, 1000, to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011100045');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6360, to_date('13-10-2021', 'dd-mm-yyyy'), 'یطسیشی', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011500068');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6362, to_date('13-10-2021', 'dd-mm-yyyy'), 'یسبب', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011500075');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6363, to_date('13-10-2021', 'dd-mm-yyyy'), 'یسبب', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011500076');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6364, to_date('13-10-2021', 'dd-mm-yyyy'), 'یسبب', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10010100077');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6651, to_date('16-10-2021', 'dd-mm-yyyy'), 'fsdfsdf', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011500078');

insert into fndc.financial_document (ID, DOCUMENT_DATE, DESCRIPTION, FINANCIAL_DOCUMENT_STATUS_ID, AUTOMATIC_FLAG, ORGANIZATION_ID, FINANCIAL_DOCUMENT_TYPE_ID, FINANCIAL_PERIOD_ID, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_DEPARTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PERMANENT_DOCUMENT_NUMBER, DOCUMENT_NUMBER) values (6652, to_date('16-10-2021', 'dd-mm-yyyy'), 'dsd', 1, 0, 1, 151, 13172, 2, 1, 1000, 1000, to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null, '10011500079');

-------------------------------------------------------------------------
insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (7201, 1, 1, 'تست', 1, 4102, '#11ab8f', 1000, 1000, to_date('10-08-2021 21:34:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-08-2021 21:34:45', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 1, 0);

insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (8452, 6, 3, 'سسسس', 4, 4102, '#191970', 1000, 1000, to_date('21-09-2021 12:57:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-11-2021 09:17:04', 'dd-mm-yyyy hh24:mi:ss'), 2, null, 1, 0);

insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (9601, 18, 3, '2کاظمی تست', 5, 7001, '#FFFACD', 1000, 1000, to_date('10-10-2021 17:16:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 14:10:38', 'dd-mm-yyyy hh24:mi:ss'), 2, null, 1, 0);

insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (5207, 4, 4, 'تفصیلی', 10, 7001, '#FFFF00', 1000, 1000, to_date('15-07-2021 09:58:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-09-2021 10:27:37', 'dd-mm-yyyy hh24:mi:ss'), 4, null, 1, 0);

insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (3, 4, 4, 'حساب معین', 10, 4101, '#33a5de', 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('11-07-2021 13:44:36', 'dd-mm-yyyy hh24:mi:ss'), 4, null, 1, 0);

insert into fnac.financial_account_structure (ID, SEQUENCE, DIGIT_COUNT, DESCRIPTION, SUM_DIGIT, FINANCIAL_CODING_TYPE_ID, COLOR, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FLG_SHOW_IN_ACC, FLG_PERMANENT_STATUS) values (4, 5, 5, 'حساب تفضیلی', 15,4101, '#1972d1', 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null, 1, 0);

-------------------------------------------------------------------------
insert into fnac.account_nature_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, 'بدون ماهیت', 1000 , 1000, to_date('29-09-2021', 'dd-mm-yyyy'), to_date('29-09-2021', 'dd-mm-yyyy'), 1, null);

insert into fnac.account_nature_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3, 'بستانکار طی دوره', 1000 , 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

------------------------------------------------------------------------------

insert into fnac.account_relation_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (2, 'بانک و سفارش خرید و پروژه', 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

insert into fnac.account_relation_type (ID, DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3, 'مشتری و سفارش فروش', 1000 ,1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

-----------------------------------------------------------------------------------------------

insert into fnac.account_permanent_status (ID, CODE, DESCRIPTION, DELETED_DATE, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION) values (1, '1', 'دائمی', null, 1000, 1000, to_date('29-09-2021', 'dd-mm-yyyy'), to_date('29-09-2021', 'dd-mm-yyyy'), 1);

insert into fnac.account_permanent_status (ID, CODE, DESCRIPTION, DELETED_DATE, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION) values (2, '2', 'موقت', null, 1000, 1000, to_date('29-09-2021', 'dd-mm-yyyy'), to_date('29-09-2021', 'dd-mm-yyyy'), 1);

insert into fnac.account_permanent_status (ID, CODE, DESCRIPTION, DELETED_DATE, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION) values (3, '3', 'انتظامی', null, 1000, 1000, to_date('29-09-2021', 'dd-mm-yyyy'), to_date('29-09-2021', 'dd-mm-yyyy'), 1);

--------------------------------------------------------------------------------------------------

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (7005, 1, 4, 'شرح کامل حساب مالی 30', '35340', 'شرح حساب مالی 30', null, 1, null, 3, null, null, null, null, null, null, 1000, 1000, to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, null);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (7011, 1, 3, 'شرح کامل حساب مالی 30', '7352', 'فرزند 6556', null, 1, null, 2, 7005, null, null, 1, 0, null, 1000, 1000, to_date('01-09-2021 13:46:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 13:46:49', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, 1);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (11002, 1, 4, 'شرح کامل حساب مالی 555', '1001004000', 'شرح حساب مالی 555', null, 1, null, 2, null, null, null, null, null, null, 1000, 1000, to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, null);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (11005, 1, 8452, 'شرح کامل حساب مالی 555', '33344444', 'حساب اول', null, 1, 0, 2, null, null, 0, 0, 0, null, 1000, 1000, to_date('29-09-2021 10:44:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-09-2021 10:44:25', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, null);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (9054, 1, 5207, null, '36778', 'فرزند 2 حساب معین', null, 1, 0, 2, null, null, 1, 0, 0, 7005, 1000, 1000, to_date('12-09-2021 12:33:18', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-09-2021 12:33:18', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, null);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (14001, 1, 9601, null, '5678554', 'aaa', null, 1, 0, 2, 9054, null, 0, 0, 0, null, 1000, 1000, to_date('13-10-2021 11:45:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:45:51', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, 1);

insert into fnac.financial_account (ID, ORGANIZATION_ID, FINANCIAL_ACCOUNT_STRUCTURE_ID, FULL_DESCRIPTION, CODE, DESCRIPTION, LATIN_DESCRIPTION, ACCOUNT_NATURE_TYPE_ID, RELATED_TO_OTHERS_FLAG, ACCOUNT_RELATION_TYPE_ID, FINANCIAL_ACCOUNT_PARENT_ID, RELATED_TO_FUND_TYPE, REFERENCE_FLAG, CONVERT_FLAG, EXCHANGE_FLAG, ACCOUNT_ADJUSTMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DISABLE_DATE, ACCOUNT_PERMANENT_STATUS_ID) values (14051, 1, 7201, 'شرح کامل حساب مالی ', '9', 'شرح حساب مالی ', null, 3, null, 2, null, null, null, null, null, null, 1000, 1000, to_date('13-10-2021 14:32:16', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 14:32:16', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null, 1);



-------------------------------------------------------------------------------

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (8122, 1, '3', 7201, 7005, 1000, 1000, to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null);

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (8125, 4, '35340', 3, 7005, 1000, 1000, to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 7005);

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (8126, 5, '35340', 4, 7005, 1000, 1000, to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-09-2021 13:44:27', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 7005);

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (12303, 1, '1', 7201, 11002, 1000, 1000, to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null);

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (12306, 4, '1001004000', 3, 11002, 1000, 1000, to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 11002);

insert into FNAC.ACCOUNT_STRUCTURE_LEVEL (ID, STRUCTURE_LEVEL, STRUCTURE_LEVEL_CODE, FINANCIAL_STRUCTURE_ID, FINANCIAL_ACCOUNT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, RELATED_ACCOUNT_ID) values (12307, 5, '1001004000', 4, 11002, 1000, 1000, to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-09-2021 13:10:13', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 11002);

-----------------------------------------------------------------

insert into fnac.centric_account_type (ID, CODE, DESCRIPTION, AUTO_CODE_GENERATE_FLAG, AUTO_INSERT_FLAG, FIX_FLAG, PRE_CODE, START_CODE, ACTIVE_FLAG, DOCUMENT_RELATED_FLAG, PARRENT_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, '10', 'اشخاص', 0, 0, 1, '01', '100', 1, 1, 1, 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

insert into fnac.centric_account_type (ID, CODE, DESCRIPTION, AUTO_CODE_GENERATE_FLAG, AUTO_INSERT_FLAG, FIX_FLAG, PRE_CODE, START_CODE, ACTIVE_FLAG, DOCUMENT_RELATED_FLAG, PARRENT_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (7, '40', 'بانک', 0, 0, 1, '07', '100', 1, 1, 1, 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

insert into fnac.centric_account_type (ID, CODE, DESCRIPTION, AUTO_CODE_GENERATE_FLAG, AUTO_INSERT_FLAG, FIX_FLAG, PRE_CODE, START_CODE, ACTIVE_FLAG, DOCUMENT_RELATED_FLAG, PARRENT_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8, '50', 'حساب بانکی', 0, 0, 1, '08', '100', 1, 1, 1, 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('22-05-2021', 'dd-mm-yyyy'), 1, null);

----------------------------------------------------------------------------------------------------------------------

insert into fnac.centric_account (ID, CODE, NAME, ACTIVE_FLAG, ABBREVIATION_NAME, LATIN_NAME, CENTRIC_ACCOUNT_TYPE_ID, ORGANIZATION_ID, PERSON_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PARENT_CENTRIC_ACCOUNT_ID) values (6151, '0610012', '2ابراهیم ستاری', 1, null, null, 1, 1, 1000, 1000, 1000, to_date('22-09-2021 11:32:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 11:32:37', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null);

insert into fnac.centric_account (ID, CODE, NAME, ACTIVE_FLAG, ABBREVIATION_NAME, LATIN_NAME, CENTRIC_ACCOUNT_TYPE_ID, ORGANIZATION_ID, PERSON_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PARENT_CENTRIC_ACCOUNT_ID) values (6801, '323', 'dsfdsffsdf', 1, null, null, 7, 1, 2, 1000, 1000, to_date('22-09-2021 13:24:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('04-10-2021 10:16:01', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);

insert into fnac.centric_account (ID, CODE, NAME, ACTIVE_FLAG, ABBREVIATION_NAME, LATIN_NAME, CENTRIC_ACCOUNT_TYPE_ID, ORGANIZATION_ID, PERSON_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PARENT_CENTRIC_ACCOUNT_ID) values (6109, '3659', 'مهلا رستمی', 1, null, null, 7, 1, 2, 1000, 1000, to_date('22-09-2021 10:57:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 10:57:41', 'dd-mm-yyyy hh24:mi:ss'), 0, null, null);

insert into fnac.centric_account (ID, CODE, NAME, ACTIVE_FLAG, ABBREVIATION_NAME, LATIN_NAME, CENTRIC_ACCOUNT_TYPE_ID, ORGANIZATION_ID, PERSON_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, PARENT_CENTRIC_ACCOUNT_ID) values (5, '05100', 'تست', 1, null, null, 7, 1, 40006, 1000, 1000, to_date('22-05-2021', 'dd-mm-yyyy'), to_date('21-09-2021 16:49:48', 'dd-mm-yyyy hh24:mi:ss'), 3, null, null);


-------------------------------------------------------------------

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9302, 6304, 6, 365000.000, 0.000, 'اسناد قرارداد بیمه', 9054, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:23:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:23:33', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6353, 6303, 1, 1333200000.000, 0.000, 'سند اول', 7011, null, null, null, null, null, null, 1000, 1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('18-10-2021 11:19:42', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8308, 8257, 1, 19749136.000, 0.000, 'ثث', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8309, 8257, 1, 0.000,19749136.000, 'فف', 11005, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 15:01:12', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:01:12', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8311, 8258, 1, 7399926.000, 0.000, 'واق', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 15:03:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:03:49', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9301, 6304, 10, 36550000.000, 0.000, 'سند حساب های قبوض پرداخت شده', 14001, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:21:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:23:02', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9303, 6304, 7, 3650000.000, 0.000, 'شرح', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:25:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:59:36', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9304, 6304, 9, 15000000.000, 0.000, 'سند حساب های قبوض پرداخت شده', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:26:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:59:44', 'dd-mm-yyyy hh24:mi:ss'), 1, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9305, 6304, 1, 333.000, 0.000, 'سند حساب های قبوض پرداخت شده', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:32:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:32:36', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9306, 6304, 4, 3333.000, 0.000, 'شرح', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:34:37', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:34:37', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9307, 6304, 2, 444.000, 0.000, 'شرح', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:35:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:35:24', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9308, 6304, 5, 3333.000, 0.000, 'اسناد قرارداد بیمه', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:35:45', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:35:45', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (9309, 6304, 3, 2222.000, 0.000, 'اسناد قرارداد بیمه', 14051, 6109, null, null, null, null, null, 1000, 1000, to_date('26-10-2021 12:59:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:59:20', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6354, 6304, 11, 14558799600.000, 0.000, 'پرداخت هزینه و خدمات', 14051, 6109, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:20:28', 'dd-mm-yyyy hh24:mi:ss'), 7, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6355, 6304, 8, 7599442.000, 0.000, 'سنتنت', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6356, 6305, 1, 14478552.000, 0.000, 'یسی', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6357, 6306, 2, 236530.000, 0.000, 'پرداخت هزینه و خدمات', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6358, 6307, 1, 750329.000, 0.000, 'پرداخت هزینه و خدمات', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6360, 6307, 1, 237499925.000, 0.000, 'پرداخت هزینه و خدمات', 11002, 6109, null, null, null, null, null, 1000, 1000, to_date('12-10-2021 12:31:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:31:47', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8304, 8256, 1, 0.000, 1234554321.000, 'fff', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8306, 8256, 2, 493777284.000, 0.000, 'پرداخت هزینه و خدمات', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 14:43:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 14:43:24', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8310, 8258, 1, 1111110.000, 0.000, 'میو', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (10313, 6304, 12, 2222.000, 0.000, 'ردیف12', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('17-11-2021 09:38:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2021 09:38:32', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6359, 6307, 1, 149296.000, 0.000, 'ردیف12', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('12-10-2021 12:31:09', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:31:09', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6405, 6360, 1, 18865296336.000, 0.000, 'سند', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6406, 6362, 1, 241110870.000, 0.000, 'ردیف12', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6407, 6363, 1, 241110870.000, 0.000, 'سند', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (6408, 6364, 1, 241110870.000, 0.000, 'سند22', 7005, 6151, 6801, null, null, null, null, 1000, 1000, to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (7401, 6651, 1, 18777589937216.000, 0.000, 'سند', 14051, 6151, 5, null, null, null, null, 1000, 1000, to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (7402, 6652, 1, 1869906455032.000, 0.000, 'ردیف587', 14051, 6151, 5, null, null, null, null, 1000, 1000, to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_document_item (ID, FINANCIAL_DOCUMENT_ID, SEQUENCE_NUMBER, DEBIT_AMOUNT, CREDIT_AMOUNT, DESCRIPTION, FINANCIAL_ACCOUNT_ID, CENTRIC_ACCOUNT_ID_1, CENTRIC_ACCOUNT_ID_2, CENTRIC_ACCOUNT_ID_3, CENTRIC_ACCOUNT_ID_4, CENTRIC_ACCOUNT_ID_5, CENTRIC_ACCOUNT_ID_6, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (10312, 6307, 4, 444444.000, 0.000, 'ردیف2', 14051, 5, null, null, null, null, null, 1000, 1000, to_date('17-11-2021 09:09:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2021 09:09:28', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

-------------------------------------------------------------------------------

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3053, 1, 6303, 1000, 1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '101211400072000059');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3054, 2, 6303, 1000, 1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10211500060');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (5005, 2, 8257, 1000, 1000, to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011100044');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3055, 1, 6304, 1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '101211400072000060');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3056, 2, 6304, 1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10211500061');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3057, 1, 6305, 1000, 1000, to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '101211400072000061');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3058, 2, 6305, 1000, 1000, to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:59:17', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10211500062');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3059, 1, 6306, 1000, 1000, to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '101211400072000062');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3060, 2, 6306, 1000, 1000, to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10211500063');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3061, 1, 6307, 1000, 1000, to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '101211400072000063');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3062, 2, 6307, 1000, 1000, to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 12:30:46', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10211500064');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (5004, 2, 8256, 1000, 1000, to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 14:43:04', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011100043');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (5006, 2, 8258, 1000, 1000, to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:03:21', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011100045');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3105, 2, 6360, 1000, 1000, to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 08:51:24', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011500068');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3106, 2, 6362, 1000, 1000, to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:35', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011500075');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3107, 2, 6363, 1000, 1000, to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:40:53', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011500076');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (3108, 2, 6364, 1000, 1000, to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-10-2021 11:41:14', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10010100077');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (4101, 2, 6651, 1000, 1000, to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:36:32', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011500078');

insert into fndc.financial_document_number (ID, FINANCIAL_NUMBERING_TYPE_ID, FINANCIAL_DOCUMENT_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, DOCUMENT_NUMBER) values (4102, 2, 6652, 1000, 1000, to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), to_date('16-10-2021 15:42:20', 'dd-mm-yyyy hh24:mi:ss'), 0, null, '10011500079');

---------------------------------------------------------

insert into fndc.FINANCIAL_CONFIG (ID, ORGANIZATION_ID, FINANCIAL_DEPARTMENT_ID, USER_ID, FINANCIAL_DOCUMENT_TYPE_ID, DOCUMENT_DESCRIPTION, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_PERIOD_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1152, 1, 5, 1000, 101, 'sddd', 2, 13172, 1000, 1000, to_date('22-09-2021 12:54:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('22-09-2021 14:56:47', 'dd-mm-yyyy hh24:mi:ss'), 1, to_date('22-09-2021 14:56:47', 'dd-mm-yyyy hh24:mi:ss'));


insert into fndc.FINANCIAL_CONFIG (ID, ORGANIZATION_ID, FINANCIAL_DEPARTMENT_ID, USER_ID, FINANCIAL_DOCUMENT_TYPE_ID, DOCUMENT_DESCRIPTION, FINANCIAL_LEDGER_TYPE_ID, FINANCIAL_PERIOD_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1954, 1, 5, 1000, 101, '??? ???', 2, 13172, 1000, 1000, to_date('17-10-2021 10:30:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-10-2021 10:30:34', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

--------------------------------------------------------

insert into fndc.Financial_Department_Ledger (ID, FINANCIAL_DEPARTMENT_ID, FINANCIAL_LEDGER_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (4102, 1, 2, 1000, 1000, to_date('21-09-2021 15:26:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('21-09-2021 15:26:01', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.financial_department_ledger (ID, FINANCIAL_DEPARTMENT_ID, FINANCIAL_LEDGER_TYPE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (3506, 4, 5, 1000, 1000, to_date('30-08-2021 10:05:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('28-11-2021 10:21:50', 'dd-mm-yyyy hh24:mi:ss'), 4, null);

----------------------------------------------------------

insert into fndc.FINANCIAL_DOCUMENT_REFRENCE (ID, FINANCIAL_DOCUMENT_ITEM_ID, REFRENCE_NUMBER, REFRENCE_DATE, REFRENCE_DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (8851, 9302, 12, to_date('26-10-2021', 'dd-mm-yyyy'), 'me too', 1000, 1000, to_date('26-10-2021 12:23:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('26-10-2021 12:23:33', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.FINANCIAL_DOCUMENT_REFRENCE (ID, FINANCIAL_DOCUMENT_ITEM_ID, REFRENCE_NUMBER, REFRENCE_DATE, REFRENCE_DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (5903, 6354, 22, to_date('12-10-2021', 'dd-mm-yyyy'), '??',1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.FINANCIAL_DOCUMENT_REFRENCE (ID, FINANCIAL_DOCUMENT_ITEM_ID, REFRENCE_NUMBER, REFRENCE_DATE, REFRENCE_DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (5904, 6355, 434, to_date('12-10-2021', 'dd-mm-yyyy'), '????',1000,1000, to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

insert into fndc.FINANCIAL_DOCUMENT_REFRENCE (ID, FINANCIAL_DOCUMENT_ITEM_ID, REFRENCE_NUMBER, REFRENCE_DATE, REFRENCE_DESCRIPTION, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (5902, 6353, 1, to_date('12-10-2021', 'dd-mm-yyyy'), 'شرح',1000,1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), 0, null);

-----------------------------------------------------------

insert into fncr.money_type (ID, CODE, DESCRIPTION, SHORT_DESCRIPTION, ACTIVE_FLAG, NATIONAL_CURRENCY_FLAG, SYMBOL, IS_BASE_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (1, '1', 'ریال', 'RL', 1, 0, 'R', 0, 1000, 1000, to_date('11-05-2021', 'dd-mm-yyyy'), to_date('11-05-2021', 'dd-mm-yyyy'), 0, null);

-----------------------------------------------------

insert into fncr.money_pricing_refrence (ID, CODE, DESCRIPTION, INTERNATIONAL_FLAG, ACTIVE_FLAG, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE) values (4, 4, 'بین المللی', 1, 1, 1000,1000, to_date('07-07-2021', 'dd-mm-yyyy'), to_date('25-07-2021', 'dd-mm-yyyy'), 0, null);

--------------------------------------------------------

insert into fndc.FINANCIAL_DOCUMENT_ITEM_CURRENCY (ID, FINANCIAL_DOCUMENT_ITEM_ID, FOREIGN_DEBIT_AMOUNT, EXCHANGE_RATE, MONEY_TYPE_ID, MONEY_PRICING_REFRENCE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FOREIGN_CREDIT_AMOUNT) values (5653, 6354, 1200.000, 12132333, 1, 4, 1000, 1000, to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:55:54', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 0.000);

insert into fndc.FINANCIAL_DOCUMENT_ITEM_CURRENCY (ID, FINANCIAL_DOCUMENT_ITEM_ID, FOREIGN_DEBIT_AMOUNT, EXCHANGE_RATE, MONEY_TYPE_ID, MONEY_PRICING_REFRENCE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FOREIGN_CREDIT_AMOUNT) values (5654, 6355, 3434.000, 2213, 1, 4, 1000, 1000, to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 0.000);

insert into fndc.FINANCIAL_DOCUMENT_ITEM_CURRENCY (ID, FINANCIAL_DOCUMENT_ITEM_ID, FOREIGN_DEBIT_AMOUNT, EXCHANGE_RATE, MONEY_TYPE_ID, MONEY_PRICING_REFRENCE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FOREIGN_CREDIT_AMOUNT) values (5652, 6353, 1200000.000, 1111, 1, 4,1000,1000, to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-10-2021 11:17:39', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 0.000);

insert into fndc.financial_document_item_currency (ID, FINANCIAL_DOCUMENT_ITEM_ID, FOREIGN_DEBIT_AMOUNT, EXCHANGE_RATE, MONEY_TYPE_ID, MONEY_PRICING_REFRENCE_ID, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, FOREIGN_CREDIT_AMOUNT) values (7608, 8308, 4444.000, 4444, 1, 4, 1000, 1000, to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-10-2021 15:00:52', 'dd-mm-yyyy hh24:mi:ss'), 0, null, 0.000);

-------------------------------------------------------
insert into fndc.NUMBERING_FORMAT_SERIAL (ID, NUMBERING_FORMAT_ID, LAST_SERIAL, CREATOR_ID, LAST_MODIFIER_ID, CREATION_DATE, LAST_MODIFICATION_DATE, VERSION, DELETED_DATE, SERIAL_RESETER, SERIAL_LENGTH) values (1053, 1002, 61, 1000, 1000, to_date('17-10-2021 17:38:31', 'dd-mm-yyyy hh24:mi:ss'), to_date('17-11-2021 12:48:29', 'dd-mm-yyyy hh24:mi:ss'), 22, null, '13991212', 5);


