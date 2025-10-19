import { useMutation } from '@tanstack/react-query';
import { registerUserAPI } from '../../api/User';
export const useRegisterUser = () => {
  return useMutation({
    mutationFn: registerUserAPI,
  });
};
