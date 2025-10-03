import React from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
const SearchInput = () => {
  return (
    <div>
      <div className='flex justify-between items-center border-2  border-amber-300 rounded-2xl'>
        <div className='pl-3'>
          <AiOutlineSearch className='text-3xl text-amber-400' />
        </div>
        <input
          type='text'
          className='w-65 h-12 p-3 border-none focus:outline-none'
          placeholder='라멘집 검색'
        />
      </div>
    </div>
  );
};

export default SearchInput;
