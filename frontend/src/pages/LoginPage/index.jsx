import React from 'react';

const LoginPage = () => {
  return (
    <>
      <div className=' flex justify-center items-center'>
        <div className=' flex flex-col justify-center mt-40'>
          <h1 className='text-center text-3xl mb-3'>라멘땅</h1>
          <form>
            <div className='mb-2'>
              <input
                type='text'
                id='id'
                placeholder='아이디'
                className='border-2 border-amber-300 w-60 h-10 rounded-lg pl-3 focus:outline-none focus:border-amber-400 transition-colors duration-200'
              />
            </div>
            <div className='mb-5'>
              <input
                type='password '
                id='password'
                placeholder='비밀번호'
                className='border-2 border-amber-300 w-60 h-10 rounded-lg pl-3 focus:outline-none focus:border-amber-400 transition-colors duration-200'
              />
            </div>
            <div>
              <button className=' w-60 h-10 rounded-lg bg-amber-300 text-white text-lg'>
                로그인
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default LoginPage;
