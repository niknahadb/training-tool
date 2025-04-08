// import '@/styles/Tile.css'; // Custom CSS for styling
// import '@/styles/fonts.css'; // Global CSS for styling

import { PlusOutlined, UserOutlined } from '@ant-design/icons';
import {
  AutoComplete,
  Avatar,
  Button,
  Card,
  Input,
  Modal,
  Progress,
  Typography,
} from 'antd';
import React, { useState } from 'react';

import {
  useCreateCourseProgressMutation,
  useFilterCoursesQuery,
  useGetAllCourseQuery,
} from '@/store';
import { useGetAllCourseProgressQuery } from '@/store/apis/courseProgressApi';
import type { Course } from '@/types/course/course';
import type { CourseProgress } from '@/types/courseProgress/courseProgress';

const { Text } = Typography;

type UserTileProps = {
  name: string;
  userRole: string;
  pfp: string;
  selected: boolean;
  ID: number;
  // courseNames?: String[];
};

const UserTile: React.FC<UserTileProps> = ({ name, ...props }) => {
  // #region background color logic
  let backgroundColor = 'rgba(255, 255, 255, 0.30)';
  if (props.userRole === '#ProjectManager') {
    backgroundColor = 'rgba(255, 112, 112, 0.40)';
  } else if (props.userRole === '#Developer') {
    backgroundColor = 'rgba(101, 222, 249, 0.40)';
  } else if (props.userRole === '#Designer') {
    backgroundColor = 'rgba(208, 80, 229, 0.40)';
  }
  // #endregion background color logic

  // #region MODAL LOGIC

  const [loading, setLoading] = useState(false);
  const [open, setOpen] = useState(false);

  const showModal = () => {
    setOpen(true);
  };

  const [createCourseProgress] = useCreateCourseProgressMutation();

  const { data: courseProgresses, refetch } = useGetAllCourseProgressQuery();

  const designCourseQuery: Partial<Course> = {
    type: 'DESIGN',
  };

  const softwareCourseQuery: Partial<Course> = {
    type: 'SOFTWARE',
  };

  const { data: softwareCourses } = useFilterCoursesQuery(softwareCourseQuery);
  const { data: designCourses } = useFilterCoursesQuery(designCourseQuery);
  const { data: courses } = useGetAllCourseQuery();

  const findCourseID = (courseName: string): number | undefined => {
    const course = courses?.find((c: Course) => c.name === courseName);
    return course?.id;
  };

  const [selectedValue, setSelectedValue] = useState(null);

  const handleSelect = (value: any) => {
    console.log('Selected value:', value);
    setSelectedValue(value);
  };

  const OnSubmit = async (courseName: any, userID: any) => {
    const courseID = findCourseID(courseName);
    if (!courseID) {
      console.error('Course ID not found for course name:', courseName);
      return;
    }

    const courseProgress: Partial<CourseProgress> = {
      courseId: courseID,
      assigned: true,
      userId: userID,
    };
    console.log('Course Name', courseName);
    console.log('User ID', userID);
    await createCourseProgress(courseProgress);
    await refetch();
  };

  const handleOk = (value: any, userID: number) => {
    if (value === null) {
      console.log('No course selected');
    } else {
      setLoading(true); // Start loading
      OnSubmit(value, userID)
        .then(() => {
          refetch(); // Refetch data after submitting
          setOpen(false); // Close modal after completing
          setSelectedValue(null); // Reset selected value
        })
        .catch((error) => {
          console.error('Error adding course:', error);
        })
        .finally(() => {
          setLoading(false); // Stop loading
        });
    }
    setOpen(false);
  };

  const handleCancel = () => {
    setSelectedValue(null);
    setOpen(false);
  };

  // #endregion MODAL LOGIC

  // const userDesignerQuery: UserQuery = {
  //   cohortId,
  //   teamId,
  //   role: 'DESIGN',
  // };

  const softwareCourseNames =
    softwareCourses?.map((course: Course) => course.name) || [];
  const designCourseNames =
    designCourses?.map((course: Course) => course.name) || [];

  // #region AUTOCOMPLETE LOGIC BASIC
  const renderTitle = (title: string) => (
    <span>
      {title}
      <a
        style={{ float: 'right' }}
        href="https://www.google.com/search?q=antd"
        target="_blank"
        rel="noopener noreferrer"
      >
        more
      </a>
    </span>
  );

  const renderItem = (title: string, count: number) => ({
    value: title,
    label: (
      <div
        style={{
          display: 'flex',
          justifyContent: 'space-between',
        }}
      >
        {title}
        <span>
          <UserOutlined /> {count}
        </span>
      </div>
    ),
  });

  // const softwareCourseOptions =
  //   softwareCourses?.map((course: Course) => renderItem(course.name, 10)) || [];
  // const designCourseOptions =
  //   designCourses?.map((course: Course) => renderItem(course.name, 10)) || [];

  const softwareCourseOptions =
    softwareCourses?.map((course: Course) => ({
      key: course.id,
      value: course.name,
    })) || [];
  const designCourseOptions =
    designCourses?.map((course: Course) => ({
      key: course.id,
      value: course.name,
    })) || [];

  const options = [
    {
      label: 'Software',
      options: softwareCourseOptions,
    },
    {
      label: 'Design',
      options: designCourseOptions,
    },
  ];

  // #endregion AUTOCOMPLETE LOGIC BASIC

  const { data: courseProgress } = useGetAllCourseProgressQuery();
  const userCourses: Course[] = [];

  const userProgressPercentges: number[] = [];
  if (courseProgress) {
    courseProgress.forEach((progress: CourseProgress) => {
      if (progress.userId === props.ID) {
        const course = courses?.filter(
          (c: Course) => c.id === progress.courseId,
        );
        if (course && course[0]) {
          userCourses.push(course[0]);
        }
      }
    });
  }

  //   const userCourseNames: String[] = [];
  //   userCourseIds.forEach((courseId) => {
  //     const { data: courses } = useFetchCourseQuery({ id: courseId });
  //     if (courses[0]) {
  //       userCourses.push(courses[0].name);
  //     }
  //   });
  //   return userCourseNames;
  // };

  return (
    <div>
      <style>
        {`
          @import url('https://fonts.googleapis.com/css?family=Poppins');

          .tile-card {
            padding: 16px;
            border-radius: 6px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            background-color: green;
            font-family: 'Poppins', sans-serif;
          }

          .tile-content {
            display: flex;
            align-items: center;
            justify-content: space-between;
          }

          .tile-left {
            display: flex;
            align-items: center;
          }

          .tile-info {
            margin-left: 16px;
          }

          .tile-name {
            display: block;
          }

          .tile-role {
            display: block;
            padding: 4px 8px;
            margin-top: 4px;
            border-radius: 6px;
            background-color: white;
            color: black;
          }

          .tile-center {
            flex-grow: 1;
            text-align: left;
            margin-left: 300px;
            margin-right: 24px;
          }

          .tile-right {
            display: flex;
            align-items: center;
            flex-direction: column;
          }

          .tile-right .ant-progress {
            margin-bottom: 8px;
          }

          .tile-button {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 8px;
          }
        `}
      </style>
      <Card
        // className={`tile-card ${props.selected ? 'bg-blue-500 text-white' : 'bg-white text-black'}`}
        className="tile-card"
        style={{
          backgroundColor: props.selected
            ? 'rgba(255, 112, 112, 0.30)'
            : 'white',
        }}
      >
        <div className="tile-content">
          <div className="tile-left">
            <Avatar size={64} src={props.pfp} />{' '}
            <div className="tile-info">
              <Text strong>{name}</Text>
              {/* <p>
                {' '}
                software:
                {softwareCourses?.map((course: Course) => (
                  <p key={course.name}>{course.name}</p>
                ))}
                design:
                {designCourses?.map((course: Course) => (
                  <p key={course.name}>{course.name}</p>
                ))}
              </p> */}
              <Text
                // type="secondary"
                className="tile-role"
                style={{ backgroundColor }}
              >
                {props.userRole}
              </Text>
            </div>
          </div>
          <div className="tile-center">
            <Text strong>
              {userCourses.map((course: Course) => (
                <p key={course.id}>{course.name}</p>
              ))}
            </Text>
            <div
              style={{
                marginTop: '15px',
              }}
            >
              <Button
                type="primary"
                onClick={showModal}
                shape="circle"
                icon={<PlusOutlined />}
              />
              <Modal
                open={open}
                title="Add Courses"
                onOk={() => handleOk(selectedValue, props.ID)} // Pass function reference
                onCancel={handleCancel}
                footer={[
                  <Button key="back" onClick={handleCancel}>
                    Return
                  </Button>,
                  <Button
                    key="submit"
                    type="primary"
                    loading={loading} // REMOVE MAYBE? LOADING EFFECT WHILE COURSES ARE BEING ADDED?
                    onClick={() => handleOk(selectedValue, props.ID)} // Pass function reference
                  >
                    Add Courses
                  </Button>,
                ]}
              >
                {/* this is where modal content goes */}
                {/* {courses?.map((course: Course) => <p>{course.name}</p>)} */}
                <AutoComplete
                  popupClassName="certain-category-search-dropdown"
                  popupMatchSelectWidth={500}
                  style={{ width: 250 }}
                  options={options}
                  filterOption
                  size="large"
                  // onSelect={handleSelect}
                  onSelect={(value) => handleSelect(value)}
                  value={selectedValue}
                >
                  <Input.Search size="large" placeholder="Search Courses" />
                </AutoComplete>
              </Modal>

              <Text> Assign New Course</Text>
            </div>
          </div>
          <div className="tile-right">
            <Progress type="circle" percent={75} width={40} />
          </div>
        </div>
      </Card>
    </div>
  );
};

export default UserTile;
