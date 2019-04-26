# 域名：http://www.123yd.cn



# 接口

### 1 .  获取与本HUB相关的上课班级状态列表

1. URL:/fitness/V2/Pelesson/getLessonInfo
2. 请求字段：

| 字段      | 含义                               |
| --------- | ---------------------------------- |
| serial_no | 设备唯一号                         |
| info_type | 获取信息内容:1.update_time, 2. all |

3. 请求结果：

   ```json
   {
   	"errorcode": "0",
   	"errormsg": "",
   	"result": {
           "update_time":"2018-06-06 14:56:30",
   		"lessons":[
               {
                   "id":12,
                   "class_name":"1年级2班",
                   "page_status":0,
                   "select_number":1,
                   "fizzo_box_id":3,
                   "planed_duration":3600,
                   "delegates_number":"2,5",
                   "segments":[
                       {
                       	"offset":0,
                       	"name":"热身环节"
                       },
                       {
                       	...
                       }
                   ],
                   "students":[
               		{
                   		"id":11,
                   		"number":1,
                   		"nickname":"Raul",
                   		"avatar":"xxxxx",
                   		"gender":1,
                           "bmi":10.4,
                   		"bpm_rest":60,
                           "bpm_motion":80,
                   		"bpm_alert":190
               		},
                       {
                   		...
               		}
   				]
               },
               {
                  ... 
               }
   		]
   	}
   }
   ```

   | 字段                   | 含义                                                   |
   | ---------------------- | ------------------------------------------------------ |
   | updatetime             | 信息变化时间                                           |
   | lessons                | 课程列表                                               |
   | lesson.id              | 课程ID                                                 |
   | lesson.class_name      | 班级名称                                               |
   | lesson.page_status     | 页面状态(1.心率墙，2.班级页面，3.个人页面，4.报告页面) |
   | lesson.select_number   | 若个人页面，选择的个人编号                             |
   | lesson.fizzo_box_id    | 课程使用的手环箱id                                     |
   | lesson.planed_duration | 课程计划时间                                           |
   | delegates_number       | 代表学生学号                                           |
   | students               | 学生列表                                               |
   | student.id             | 学生ID                                                 |
   | student.number         | 学生学号                                               |
   | student.nickname       | 学生昵称                                               |
   | student.avatar         | 学生头像地址                                           |
   | student.gender         | 学生性别(1.男生，2.女生)                               |
   | student.bmi            | 学生bmi                                                |
   | student.bpm_rest       | 学生静息心率                                           |
   | student.bpm_motion     | 学生开始运动心率（对应蓝色区域）                       |
   | student.bpm_alert      | 学生报警心率阈值(对应红色区域)                         |
   | segments               | 课程环节列表                                           |
   | segments.offset        | 环节点开始时间点                                       |
   | segments.name          | 环节名称                                               |

   

### 2 . 获取上课信息

1. URL:/fitness/V2/Pelesson/getLessonHeartrates

2. 请求字段：

   | 字段                         | 含义                           |
   | ---------------------------- | ------------------------------ |
   | lesson_id                    | 课程ID                         |
   | is_get_all_students          | 是否获取所有学生的实时运动数据 |
   | is_get_alert_studens         | 是否获取报警学生列表           |
   | is_get_basic_data            | 是否获取基本运动数据           |
   | is_get_delegates_data        | 是否获取学生代表运动数据       |
   | is_get_delegates_bpms        | 是否获取代表的心率列表         |
   | delegates_offset_from        | 学生运动数据起始时间           |
   | delegates_interval           | 学生运动数据心率间隔,默认1     |
   | is_get_selected_student_data | 是否获取指定学生数据           |
   | is_get_selected_student_bpms | 是否获取指定学生的心率列表     |
   | selected_student_number      | 指定学生学号                   |
   | selected_student_offset_from | 指定学生心率数据开始时间点     |
   | selected_student_interval    | 指定学生心率数据间隔,默认1     |
   | is_get_class_bpms            | 是否获取课程心率数据           |
   | class_bpms_offset_from       | 课程平均心率数据开始           |
   | class_bpms_interval          | 课程平均心率数据间隔           |

   

3. 请求结果：

```json
{
	"errorcode": "0",
	"errormsg": "",
	"result": {
        “started_duration”: 1200,
        "all_students":{
            "bpm":"34,55,33,22,134,55,...,55",
        	"step_count":"455,333,455,3311,3355,3333,...,3333",
        	"step_frequency":"24,55,33,112,...,55",
        },
        "alert_studens":"1,3,6,33",
        "lesson_basic_data":{
            "resthr_intensity":2.5,
            "density":54,
            "bpm_avg":164,
            "alert_times":4
        },
        "delegates_data":{
            "offset_to":1122,
            "delegates":[
                {
                    "number":1,
                    "bpm":160,
                    "resthr_intensity":4.5,
                    "bpms":[
                        {
                           "offset":10,
                           "bpm":120
                        },
                        {
                            "offset":16,
                            "bpm":150
                        }
                    ],
                    "exercises":[
                        {
                            "offset_from":120,
                            "offset_to":150
                        },
                        {
                            "offset_from":220,
                            "offset_to":250
                        }
                    ]
                },
                {
                    "number":1,
                    "bpm":160,
                    "resthr_intensity":4.5,
                    "bpms":[
                        {
                           "offset":10,
                           "bpm":120
                        },
                        {
                            "offset":16,
                            "bpm":150
                        }
                    ],
                    "exercises":[
                        {
                            "offset_from":120,
                            "offset_to":150
                        },
                        {
                            "offset_from":220,
                            "offset_to":250
                        }
                    ]
                }
        	]
        },
        "selected_student":{
            "bpm":125,
            "bpm_max":180,
            "bpm_avg":156,
            "density":24,
            "resthr_intensity":2.4,
            "offset_to":455,
            "bpms":[
            	{
                	"offset":10,
                    "bpm":120
                },
                {
                	"offset":16,
                	"bpm":150
                }
             ],
             "exercises":[
             	{
                	"offset_from":120,
                    "offset_to":150
                },
                {
                 	"offset_from":220,
                	"offset_to":250
                }
             ]
        },
        “class_bpms”:{
        	"offset_to":112,
            "bpms":[
            	{
                	"offset":10,
                    "bpm":120
                },
                {
                	"offset":16,
                	"bpm":150
                }
             ]
    	}
	}
}
```

| 字段                                      | 含义                                            |
| ----------------------------------------- | ----------------------------------------------- |
| started_duration                          | 距开课多久时间                                  |
| all_students                              | is_get_all_students=1, 返回每个学生实时运动数据 |
| all_students.bpm                          | 实时心率列表                                    |
| all_students.step_count                   | 实时步数列表                                    |
| all_students.step_frequency               | 实时步频列表                                    |
| alert_students                            | 报警学生学号列表                                |
| lesson_basic_data                         | 课程基本运动数据                                |
| lesson_basic_data.resthr_intensity        | 课程平均运动强度                                |
| lesson_basic_data.density                 | 课程平均密度                                    |
| lesson_basic_data.bpm_avg                 | 课程平均心率                                    |
| lesson_basic_data.alert_times             | 课程报警次数                                    |
| delegates_data                            | 学生代表数据                                    |
| delegates_data.offset_to                  | 学生代表数据的时间截至点                        |
| delegates_data.delegates                  | 学生代表列表                                    |
| delegates_data.delegates.number           | 代表学号                                        |
| delegates_data.delegates.bpm              | 代表实时心率                                    |
| delegates_data.delegates.resthr_intensity | 代表平均强度                                    |
| delegates_data.delegates.bpms             | 代表心率列表                                    |
| delegates_data.delegates.exercises        | 代表运动区间                                    |
| selected_student                          | 指定学生运动数据                                |
| selected_student.bpm                      | 指定学生心率                                    |
| selected_student.bpm_max                  | 指定学生最高心率                                |
| selected_student.bpm_avg                  | 指定学生平均心率                                |
| selected_student.density                  | 指定学生密度                                    |
| selected_student.resthr_intensity         | 指定学生强度                                    |
| selected_student.bpms                     | 指定学生心率列表                                |
| selected_student.exercises                | 指定学生运动区间                                |
| class_bpms                                | 课程心率数据                                    |
| class_bpms.offset_to                      | 课程心率截至时间点                              |
| class_bpms.bpms                           | 课程心率列表                                    |

