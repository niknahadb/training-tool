'use client';

import 'react-quill/dist/quill.snow.css';

import React, { useEffect, useState } from 'react';
import ReactQuill from 'react-quill';

import { useGetLessonByIdQuery, useUpdateLessonByIdMutation } from '@/store';

const LessonPage = ({ params }: { params: { lessonId: number } }) => {
  const { data: lesson } = useGetLessonByIdQuery(params.lessonId);
  const [setLesson] = useUpdateLessonByIdMutation();
  const [value, setValue] = useState<any>({ ops: [] });

  useEffect(() => {
    if (lesson?.content) {
      const json = JSON.parse(lesson.content);
      setValue(json);
    }
  }, [lesson]);

  const onTextChange = (
    _input: string,
    _delta: any,
    _sources: any,
    editor: ReactQuill.UnprivilegedEditor,
  ) => {
    const textVal = editor.getContents();
    setValue(textVal);
    setLesson({ ...lesson, content: JSON.stringify(textVal) });
  };

  if (!lesson) {
    return <div />;
  }

  if (lesson.content == null) {
    return (
      <div>
        <video
          style={{ width: '100%', height: 'auto' }}
          preload="auto"
          controls
        >
          <source
            src="https://d2n1nn65fquai3.cloudfront.net/testVideo.mp4"
            type="video/mp4"
          />
          <track kind="captions" />
        </video>
      </div>
    );
  }

  return (
    <div className="app">
      <ReactQuill
        theme="snow"
        value={value}
        onChange={onTextChange}
        bounds=".app"
        placeholder="Write something..."
        formats={[
          'header',
          'font',
          'size',
          'bold',
          'italic',
          'underline',
          'strike',
          'blockquote',
          'list',
          'bullet',
          'indent',
          'link',
          'image',
          'video',
        ]}
        modules={{
          toolbar: [
            [{ header: '1' }, { header: '2' }, { font: [] }],
            [{ size: [] }],
            ['bold', 'italic', 'underline', 'strike', 'blockquote'],
            [
              { list: 'ordered' },
              { list: 'bullet' },
              { indent: '-1' },
              { indent: '+1' },
            ],
            ['link', 'image', 'video'],
            ['clean'],
          ],
          clipboard: {
            // toggle to add extra line breaks when pasting HTML:
            matchVisual: false,
          },
        }}
      />
    </div>
  );
};

export default LessonPage;
