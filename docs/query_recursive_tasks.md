## 递归查找task及其下属子task

Need MySQL 8+ 

```sql
WITH recursive id_pid_cte ( id, parent_id ) AS (
	SELECT
		t0.id,
		t0.parent_id 
	FROM
		task t0 
	WHERE
		t0.id = 1 UNION ALL
	SELECT
		t1.id,
		t1.parent_id 
	FROM
		task t1
		INNER JOIN id_pid_cte ON t1.parent_id = id_pid_cte.id 
	) SELECT
	id, parent_id 
FROM
	id_pid_cte;
```
