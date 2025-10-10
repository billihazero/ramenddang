import React from 'react';
import { Flex, Input } from 'antd';
import { AiOutlineSearch } from 'react-icons/ai';
const SearchInput = () => {
  return (
    <>
      <Flex className=' items-center border-2 w-80 border-amber-300 rounded-2xl'>
        <Flex>
          <AiOutlineSearch className='text-3xl text-amber-400' />
        </Flex>
        <Input
          type='text'
          size='large'
          variant='borderless'
          className='w-70 h-10 p-3'
          placeholder='라멘집 검색'
        />
      </Flex>
    </>
  );
};

export default SearchInput;
