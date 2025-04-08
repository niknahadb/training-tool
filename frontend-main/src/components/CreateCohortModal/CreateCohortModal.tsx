import { Form, InputNumber, Modal, Select } from 'antd';
import type { FieldValues } from 'react-hook-form';

import {
  useCreateCohortMutation,
  useGetAllCohortsQuery,
  useUpdateCohortByIdMutation,
} from '@/store';
import type { Cohort } from '@/types/cohort/cohort';

interface CreateCohortModalProps {
  isModalOpen: boolean;
  closeModal: () => void;
  type?: 'create' | 'update';
  initialValues?: Partial<Cohort>;
}

const CreateCohortModal = ({
  isModalOpen,
  closeModal,
  type = 'create',
  initialValues,
}: CreateCohortModalProps) => {
  const [createCohort] = useCreateCohortMutation();
  const [updateCohort] = useUpdateCohortByIdMutation();
  const { refetch } = useGetAllCohortsQuery();
  const [form] = Form.useForm();

  const onFormSubmit = async (data: FieldValues) => {
    const cohort: Partial<Cohort> = {
      type: data.type,
      year: data.year,
      id: initialValues?.id,
    };
    if (type === 'update') {
      await updateCohort(cohort);
    } else {
      await createCohort(cohort);
    }
    await refetch();
    closeModal();
  };

  return (
    <Modal
      title="Create Cohort"
      open={isModalOpen}
      onCancel={closeModal}
      onOk={form.submit}
      okText={type === 'update' ? 'Update' : 'Create'}
      cancelText="Cancel"
    >
      <Form form={form} layout="horizontal" onFinish={onFormSubmit}>
        <Form.Item
          label="Type"
          name="type"
          rules={[{ required: true }]}
          initialValue={initialValues?.type}
        >
          <Select>
            <Select.Option value="FALL">Fall</Select.Option>
            <Select.Option value="WINTER_SPRING">Winter/Spring</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="Year"
          name="year"
          rules={[{ required: true }]}
          initialValue={initialValues?.year}
        >
          <InputNumber />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default CreateCohortModal;
