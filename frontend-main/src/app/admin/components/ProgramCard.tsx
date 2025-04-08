import { Collapse, Space } from 'antd';

import { useFilterTeamsQuery } from '@/store';

import TeamCard from './TeamCard';

const ProgramCard = ({ programId, cohortId }: TeamApiFilter) => {
  const { data: teams } = useFilterTeamsQuery({ programId, cohortId });

  console.log('teams', teams);
  console.log('programId', programId);
  console.log('cohortId', cohortId);

  return (
    <Space direction="vertical" style={{ width: 'auto' }}>
      {teams?.map((team) => (
        <Collapse
          collapsible="header"
          defaultActiveKey={['1']}
          key={team.id}
          style={{ width: 'auto' }}
          items={[
            {
              key: team.id,
              label: team.name,
              children: <TeamCard team={team} />,
            },
          ]}
        />
      ))}
    </Space>
  );
};

export default ProgramCard;
