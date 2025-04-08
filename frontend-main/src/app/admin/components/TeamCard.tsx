import type { Team } from '@/types/team/team';

const TeamCard = ({ team }: { team: Team }) => {
  return <div>This is the team: {team.name}</div>;
};

export default TeamCard;
