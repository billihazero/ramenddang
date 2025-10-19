import axiosInstance from '../utils/axiosInstance';
export const registerUserAPI = async (body) => {
  const res = await axiosInstance.post('/user', body);
  return res.data;
};
