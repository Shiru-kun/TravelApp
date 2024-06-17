import { ReactNode } from 'react';
import styles from '../styles/pages/home/home.module.scss';

type Props = {
  children: ReactNode;
};

export default function Layout({ children }: Props) {
  return (
    <div className={styles.layout}>
      {children}
    </div>
  ); 
}
