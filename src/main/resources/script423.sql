select stud.name, stud.age, fac.name
from student stud left join faculty fac on stud.faculty_id = fac.id;
--/*from student stud cross join faculty fac on stud.faculty_id = fac.id*/
select stud.name, ava.data
from student stud inner join avatar ava on stud.id = ava.student_id;