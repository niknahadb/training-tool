import { Form, Input, Modal } from 'antd';
import type { FieldValues } from 'react-hook-form';

import {
  useCreateCourseCollectionMutation,
  useGetAllCourseCollectionQuery,
  useUpdateCourseCollectionByIdMutation,
} from '@/store';
import type { CourseCollection } from '@/types/courseCollection/courseCollection';

interface CreateCourseCollectiontModalProps {
  isModalOpen: boolean;
  closeModal: () => void;
  type?: 'create' | 'update';
  initialValues?: Partial<CourseCollection>;
}

const CreateCollectionModal = ({
  isModalOpen,
  closeModal,
  type = 'create',
  initialValues,
}: CreateCourseCollectiontModalProps) => {
  const [createCourseCollection] = useCreateCourseCollectionMutation();
  const [updateCourseCollection] = useUpdateCourseCollectionByIdMutation();
  const { refetch } = useGetAllCourseCollectionQuery();
  const [form] = Form.useForm();

  const onFormSubmit = async (data: FieldValues) => {
    const courseCollection: Partial<CourseCollection> = {
      name: data.name,
      id: initialValues?.id,
    };
    if (type === 'update') {
      await updateCourseCollection(courseCollection);
    } else {
      await createCourseCollection(courseCollection);
    }
    await refetch();
    closeModal();
  };

  return (
    <Modal
      title="Create Course Collection"
      open={isModalOpen}
      onCancel={closeModal}
      onOk={form.submit}
      okText={type === 'update' ? 'Update' : 'Create'}
      cancelText="Cancel"
    >
      <Form form={form} layout="horizontal" onFinish={onFormSubmit}>
        <Form.Item
          label="Name"
          name="name"
          rules={[{ required: true }]}
          initialValue={initialValues?.name}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default CreateCollectionModal;
