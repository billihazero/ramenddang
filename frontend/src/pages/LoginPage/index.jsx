import { Button, Flex, Form, Input, Typography } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import React from 'react';

const { Title } = Typography;

const LoginPage = () => {
  return (
    <>
      <Flex vertical align='center' className='!mt-30'>
        <Title type='secondary' level={2}>
          라멘땅
        </Title>
        <Form name='login' className=' w-80 items-center'>
          <Form.Item className='!mb-4'>
            <Input
              type='text'
              prefix={<UserOutlined />}
              size='large'
              placeholder='아이디'
              className='!border-amber-300 hover:!border-amber-400 focus-within:!border-amber-400 !transition-colors !duration-200'
            />
          </Form.Item>
          <Form.Item>
            <Input
              type='password'
              prefix={<LockOutlined />}
              size='large'
              placeholder='비밀번호'
              className='!border-amber-300 hover:!border-amber-400 focus-within:!border-amber-400 !transition-colors !duration-200'
            />
          </Form.Item>
          <Form.Item>
            <Button
              block
              htmlType='submit'
              type='text'
              size='large'
              className='!bg-amber-300 !text-white !mb-0'
            >
              로그인
            </Button>
          </Form.Item>
        </Form>
        <a href='/register' className='!text-gray-400'>
          회원가입
        </a>
      </Flex>
    </>
  );
};

export default LoginPage;
