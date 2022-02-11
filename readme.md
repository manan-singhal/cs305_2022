This code works on Sakila DB where insert, update and delete works on all the tables while selectOne and selectMany only works through Model class created. I have added city class to my model folder and checked my code for selectOne and selectMany only on City table of sakila DB.

Unit Test execution:-

String queryId = You will provide an id to execute which is present on queries.xml file.

Object queryParam = You will provide all the variables values corresponding to the queryId command line available on queries.xml file with comma separated.

Instruction to provide data in queryParam and queries.xml file:

    Query:
    <sql id="command_id4" paramType="org.foo.Bar">
		<![CDATA[
		Update city set city=${var1} where city_id=${var2} AND country_id=${var3};
		]]>
	</sql>

    queryParam:
    "\"Jaipur1\", 576, 8"
    Format: (String, int, int) i.e (city, city_id, country_id)

    here,
        var1 = "Jaipur"
        var2 = 576
        var3 = 8