import { Button, Flex } from 'antd';
import React from 'react';
import { Navigate, useNavigate } from 'react-router-dom';

const MyPage = () => {
  const navigate = useNavigate();

  return (
    <>
      <Flex align='center' justify='center' className=' !mt-70'>
        <Button
          type='primary'
          size='large'
          className='w-30 hover:!shadow-lg transition-shadow'
          onClick={() => navigate('/login')}
        >
          로그인
        </Button>
      </Flex>
    </>
  );
};

export default MyPage;
