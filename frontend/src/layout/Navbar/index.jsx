import React from 'react';
import NavItem from './Sections/NavItem';
import logoUrl from '@assets/images/logo.png';
import { Input } from 'antd';
import SearchInput from './Sections/SearchInput';
import { Flex } from 'antd';

const Navbar = () => {
  return (
    <>
      <Flex vertical>
        <Flex
          justify='space-between'
          align='center'
          className=' w-full !p-1 !mt-2 !mb-2'
        >
          <Flex className='h-15 w-15'>
            <img src={logoUrl} />
          </Flex>
          <SearchInput />
        </Flex>
        <NavItem />
      </Flex>
    </>
  );
};

export default Navbar;
