// import { Card, Divider, List } from 'antd';
// import React from 'react';

// interface TeamProgressTitleProps {
//   title: string;
//   data: { label: string; value: number }[];
// }

// const TeamProgressTitle: React.FC<TeamProgressTitleProps> = ({
//   title,
//   data,
// }) => {
//   const progressItems = data.map((item) => (
//     <List.Item key={item.label}>
//       <List.Item.Meta
//         avatar={<span style={{ fontSize: 24 }}>{item.value}%</span>}
//         title={item.label}
//       />
//     </List.Item>
//   ));

//   return (
//     <Card title={title} bordered={false} hoverable>
//       <Divider />
//       <List
//         itemLayout="horizontal"
//         dataSource={progressItems}
//         renderItem={(item) => item}
//       />
//     </Card>
//   );
// };

// export default TeamProgressTitle;

// import '@/styles/Tile.css'; // Custom CSS for styling
// import '@/styles/fonts.css'; // Global CSS for styling
// import '@/styles/TeamProgressTile.css'; // Custom CSS for styling

// const NumberTile: React.FC = (props) => {
//   return (
//   )
// }
// const TeamProgressTile: React.FC = (props) => { // props will have 3 numbers (NS,IP,S)
//   return (
//     <Card className="tile-card">
//       <div className="tile-content">
//         <div className="tile-left">
//           <Avatar size={64} src="${props.pfp}" />{' '}
//           <div className="tile-info">
//             <Text strong>{props.name}</Text>
//             <Text type="secondary" className="tile-role">
//               {props.role}
//             </Text>
//           </div>
//         </div>
//         <div className="tile-center">
//           <Text strong>Boot camp 1</Text>
//         </div>
//         <div className="tile-right">
//           <Progress type="circle" percent={75} width={40} />
//           <Button type="primary" shape="circle" icon={<PlusOutlined />} />
//           <Text>Assign New Course</Text>
//         </div>
//       </div>
//     </Card>
//   );
// };
// export default TeamProgressTile;
import '@/styles/TeamProgressTile.css';

import { Typography } from 'antd';
import React from 'react';

import {
  //  useFetchUsersQuery,
  useGetCompletedCourseProgressesQuery,
  useGetInProgressCourseProgressesQuery,
  useGetNotStartedCourseProgressesQuery,
} from '@/store/apis/courseProgressApi';
import type { CourseProgress } from '@/types/courseProgress/courseProgress';

const { Text } = Typography;

type ProgressTileProps = {
  // notStarted: number;
  // inProgress: number;
  // submitted: number;
  teamId: number;
};

const TeamProgressTile: React.FC<ProgressTileProps> = ({ ...props }) => {
  let notStarted = 0;
  let inProgress = 0;
  let submitted = 0;

  // const courseProgress: Partial<CourseProgress> = {
  //   courseId: courseID,
  //   assigned: true,
  //   userId: userID,
  // };

  // const usersQuery: Partial<User> = {
  //   teamId: props.teamId,
  // };

  // console.log('usersQuery', usersQuery);

  // const { data: users } = useFetchUsersQuery(usersQuery);
  // const teamUsers = users ?? [];

  // console.log('teamUsers', teamUsers);

  const teamQuery: Partial<CourseProgress> = {
    id: props.teamId,
  };

  const { data: notStartedData } = useGetNotStartedCourseProgressesQuery(
    props.teamId,
  );
  const { data: inProgressData } = useGetInProgressCourseProgressesQuery(
    props.teamId,
  );
  const { data: submittedData } = useGetCompletedCourseProgressesQuery(
    props.teamId,
  );

  notStarted = notStartedData?.length ?? 0;
  inProgress = inProgressData?.length ?? 0;
  submitted = submittedData?.length ?? 0;

  // for (const user of teamUsers) {
  //   const userQuery: Partial<CourseProgress> = {
  //     userId: user.id,
  //   };
  //   const { data: userSubmitted } = useGetCompletedCourseProgressesQuery(
  //     user.id,
  //   );
  //   const { data: userInProgress } = useGetInProgressCourseProgressesQuery(
  //     user.id,
  //   );
  //   const { data: userNotStarted } = useGetNotStartedCourseProgressesQuery(
  //     user.id,
  //   );

  //   submitted += userSubmitted?.length ?? 0;
  //   inProgress += userInProgress?.length ?? 0;
  //   notStarted += userNotStarted?.length ?? 0;
  // }

  // if (users) {
  //   // Filter users based on teamID
  //   const teamUsers = users.filter((user: User) => user.teamID === teamID);

  //   // Calculate the number of users in each progress state
  //   notStarted = teamUsers.filter((user: User) => user.progress === 'Not Started').length;
  //   inProgress = teamUsers.filter((user: User) => user.progress === 'In Progress').length;
  //   submitted = teamUsers.filter((user: User) => user.progress === 'Submitted').length;
  // }

  return (
    <div className="tileContainer">
      <div className="tile">
        <div className="number">{notStarted}</div>
        <div className="label">Not Started</div>
      </div>
      <div className="tile">
        <div className="number">{inProgress}</div>
        <div className="label">In Progress</div>
      </div>
      <div className="tile">
        <div className="number">{submitted}</div>
        <div className="label">Submitted</div>
      </div>
    </div>
  );
};

export default TeamProgressTile;
