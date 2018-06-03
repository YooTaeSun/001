WITH T_CON AS (
				 SELECT
				 	UCC.COLUMN_NAME,
				 	UCC.CONSTRAINT_NAME,
					US.CONSTRAINT_TYPE
				FROM
					USER_CONS_COLUMNS UCC,
					USER_CONSTRAINTS US
				WHERE 1=1 
					AND UCC.CONSTRAINT_NAME = US.CONSTRAINT_NAME
					AND UCC.TABLE_NAME = 'BONUS'
	)	
		SELECT
dao 
FROM
			(
--				SELECT
--					'BONUS' AS TABLE_NAME,
--					'N' AS AUTO_INCREMENT,
--					0 AS ORDINAL_POSITION,
--					'' COLUMN_COMMENT,
--					'' COLUMN_NAME,
--					'' COLUMN_TYPE,
--					'' COLUMN_KEY,
--					'' COLUMN_DEFAULT,
--					'' IS_NULLABLE,
--					'' FK
--				FROM
--					DUAL
--			UNION ALL 
			SELECT
					'' TABLE_NAME,
					'' AUTO_INCREMENT,
					COLUMN_ID AS ORDINAL_POSITION,
					ACC.COMMENTS AS COLUMN_COMMENT,
					'private ' || decode(DATA_TYPE,'VARCHAR2','String','CHAR','String','NUMBER','Integer') || 
						' ' || LOWER(SUBSTR(REPLACE(INITCAP(ATC.COLUMN_NAME),'_'),1,1))  
						|| SUBSTR(REPLACE(INITCAP(ATC.COLUMN_NAME),'_'),2)
						|| ';'
						||  decode(ACC.COMMENTS,null,'', ' /* ' || ACC.COMMENTS  || ' */')
						AS dao,
						LENGTH(ACC.COMMENTS),
					ATC.COLUMN_NAME AS COLUMN_NAME /* 컬럼명 */
					,DATA_TYPE AS COLUMN_TYPE /* 데이터타입 */
					,(SELECT DECODE(T_CON.CONSTRAINT_TYPE,'P','PK','R','FK','C','CK','UK','UK') 
							FROM
								T_CON
							WHERE 1=1
								AND T_CON.CONSTRAINT_TYPE = 'P'
								AND T_CON.COLUMN_NAME = ATC.COLUMN_NAME
					) AS COLUMN_KEY /* PRI MUL */
					,'' AS DATA_DEFAULT
					,DECODE( NULLABLE, 'N', 'NOT NULL', '' ) AS IS_NULLABLE /* NOT NULL */
					,(SELECT DECODE(T_CON.CONSTRAINT_TYPE,'P','PK','R','FK','C','CK','UK','UK') 
							FROM
								T_CON
							WHERE 1=1
								AND T_CON.CONSTRAINT_TYPE = 'R'
								AND T_CON.COLUMN_NAME = ATC.COLUMN_NAME
					) AS FK
				FROM
					ALL_TAB_COLUMNS ATC,
					ALL_COL_COMMENTS ACC
				WHERE
					1 = 1
					AND ATC.OWNER = ACC.OWNER
					AND ATC.TABLE_NAME = ACC.TABLE_NAME
					AND ATC.COLUMN_NAME = ACC.COLUMN_NAME
					AND ATC.TABLE_NAME = 'BONUS' /* 조건 : 테이블명  */
			) A
		ORDER BY
			A.ORDINAL_POSITION
