import bcrypt from 'bcryptjs';

export const hashPassword = async (plain) => {
  const salt = await bcrypt.genSalt(10); //임의의 문자열 생성
  return bcrypt.hash(plain, salt);
};
