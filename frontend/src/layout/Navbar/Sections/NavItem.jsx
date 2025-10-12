import { Button, Flex } from 'antd';
import React from 'react';
import { Link } from 'react-router-dom';

const tabs = [
  { key: 'search', label: '검색' },
  { key: 'my', label: 'MY' },
];

const NavItem = ({ activeTab, onChangeTab }) => {
  return (
    <Flex justify='space-around' className='bg-amber-300'>
      {tabs.map(({ key, label }) => {
        return (
          <Button
            type='link'
            size='large'
            key={key}
            onClick={() => onChangeTab(key)}
            className={`!text-white ${activeTab === key ? '!underline' : ''}`}
          >
            {label}
          </Button>
        );
      })}
    </Flex>
  );
};

export default NavItem;
