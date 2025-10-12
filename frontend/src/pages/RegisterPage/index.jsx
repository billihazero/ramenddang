import { Button, Flex, Form, Input, Typography } from 'antd';
import React from 'react';

const { Title } = Typography;

const RegisterPage = () => {
  return (
    <>
      <Flex vertical align='center' justify='center' className='h-full'>
        <Title type='secondary' level={2}>
          회원가입
        </Title>
        <Form name='register' className=' w-80 items-center'>
          <Form.Item className='!mb-2'>
            <Input type='text' size='large' placeholder='아이디' />
          </Form.Item>
          <Form.Item>
            <Input type='text' size='large' placeholder='비밀번호' />
          </Form.Item>
          <Form.Item className='!mb-2'>
            <Input type='text' size='large' placeholder='이름' />
          </Form.Item>
          <Form.Item className='!mb-2'>
            <Input type='text' size='large' placeholder='생년월일' />
          </Form.Item>
          <Form.Item>
            <Input type='text' size='large' placeholder='휴대전화' />
          </Form.Item>
          <Form.Item>
            <Button
              block
              htmlType='submit'
              type='text'
              size='large'
              className='!bg-amber-300 !text-white !mb-0'
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
