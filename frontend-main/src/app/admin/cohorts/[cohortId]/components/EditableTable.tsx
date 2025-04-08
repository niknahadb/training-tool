'use client';

import type { TableProps } from 'antd';
import { Form, Input, Select, Table, Typography } from 'antd';
import type { AnyObject } from 'immer/dist/internal';
import { useState } from 'react';

import type { Team } from '@/types/team/team';

interface EditableCellProps extends React.HTMLAttributes<HTMLElement> {
  editing: boolean;
  dataIndex: string;
  title: any;
  inputType: 'select' | 'text';
  record: Team;
  index: number;
}

const EditableCell: React.FC<React.PropsWithChildren<EditableCellProps>> = ({
  editing,
  dataIndex,
  title,
  inputType,
  record,
  index,
  children,
  ...restProps
}) => {
  const inputNode = inputType === 'select' ? <Select /> : <Input />;

  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
          style={{ margin: 0 }}
          rules={[
            {
              required: true,
              message: `Please Input ${title}!`,
            },
          ]}
        >
          {inputNode}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

function EditableTable<T extends AnyObject>({
  columns,
  dataSource,
  footer,
  updateCallback,
}: {
  columns: any[];
  dataSource: T[];
  footer?: any;
  updateCallback?: (data: Partial<T> & { id: number }) => Promise<void>;
}) {
  const [form] = Form.useForm();
  const [editingKey, setEditingKey] = useState<number>(0);

  const isEditing = (record: T) => record.id === editingKey;

  const edit = (record: Partial<T> & { id: number }) => {
    form.setFieldsValue({
      name: record.name ?? '',
      program: record.programId ?? 0,
    });
    setEditingKey(record.id);
  };

  const cancel = () => {
    console.log('cancel');
    setEditingKey(0);
  };

  const save = async (id: number) => {
    console.log('save');
    try {
      const row = (await form.validateFields()) as Partial<T>;
      if (updateCallback) {
        await updateCallback({ ...row, id });
      }
      setEditingKey(0);
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo);
    }
  };

  const editableColumns = [
    ...columns,
    {
      title: 'Actions',
      dataIndex: 'actions',
      width: 150,
      render: (_: any, record: T & { id: number }) => {
        const editable = isEditing(record);

        console.log('editable', editable, record.id, editingKey);

        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save(record.id)}
              style={{ marginRight: 8 }}
            >
              Save
            </Typography.Link>
            <Typography.Link onClick={() => cancel()}>Cancel</Typography.Link>
          </span>
        ) : (
          <Typography.Link onClick={() => edit(record)}>Edit</Typography.Link>
        );
      },
    },
  ];

  const mergedColumns: TableProps<T>['columns'] = editableColumns.map(
    (col: any) => {
      if (col.dataIndex === 'actions') {
        return col;
      }
      return {
        ...col,
        onCell: (record: T) => ({
          record,
          inputType: col.dataIndex === 'age' ? 'number' : 'text',
          dataIndex: col.dataIndex,
          title: col.title,
          editing: isEditing(record),
        }),
      };
    },
  );

  return (
    <Form form={form}>
      <Table
        components={{
          body: {
            cell: EditableCell,
          },
        }}
        columns={mergedColumns}
        dataSource={dataSource}
        pagination={false}
        footer={footer}
      />
    </Form>
  );
}

export default EditableTable;
