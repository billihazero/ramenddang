import { Button, Flex, Form, Input, message, Typography } from 'antd';
import React from 'react';
import { useRegisterUser } from '../../features/user/hooks';
import { useNavigate } from 'react-router-dom';

const { Title } = Typography;

const RegisterPage = () => {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const { mutate, isPending } = useRegisterUser();

  const onFinish = (values) => {
    mutate(values, {
      onSuccess: (res) => {
        if (res?.status) {
          message.success(res?.messages || '회원가입 성공');
          navigate('/login');
        } else {
          message.error(res?.messages || '회원가입 실패');
        }
      },
      onError: (err) => {
        message.error(err);
      },
    });
  };
  return (
    <>
      <Flex vertical align='center' justify='center' className='h-full'>
        <Title type='secondary' level={2}>
          회원가입
        </Title>
        <Form
          form={form}
          name='register'
          onFinish={onFinish}
          className=' w-80 items-center'
        >
          <Form.Item
            name='user_id'
            className='!mb-2'
            rules={[{ required: true, message: '아이디를 입력해주세요' }]}
          >
            <Input type='text' size='large' placeholder='아이디' />
          </Form.Item>
          <Form.Item
            name='user_ps'
            rules={[{ required: true, message: '비밀번호를 입력해주세요' }]}
          >
            <Input type='password' size='large' placeholder='비밀번호' />
          </Form.Item>
          <Form.Item
            name='user_nm'
            className='!mb-2'
            rules={[{ required: true, message: '이름을 입력해주세요' }]}
          >
            <Input type='text' size='large' placeholder='이름' />
          </Form.Item>
          <Form.Item
            name='user_birth'
            className='!mb-2'
            rules={[{ required: true, message: '생년월일을 입력해주세요' }]}
          >
            <Input type='text' size='large' placeholder='생년월일' />
          </Form.Item>
          <Form.Item
            name='user_telno'
            rules={[{ required: true, message: '전화번호를 입력해주세요' }]}
          >
            <Input type='text' size='large' placeholder='휴대전화' />
          </Form.Item>
          <Form.Item>
            <Button
              block
              htmlType='submit'
              type='text'
              size='large'
              className='!bg-amber-300 !text-white !mb-0'
              loading={isPending}
            >
              회원가입
            </Button>
          </Form.Item>
        </Form>
      </Flex>
    </>
  );
};

export default RegisterPage;
