import { fireEvent, render, screen } from "@testing-library/react";
import { BrowserRouter, useNavigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import Login from "../../pages/authentication/login";
import "@testing-library/jest-dom"

const mockNavigate = jest.fn();
jest.mock('react-router',()=>({
    ...jest.requireActual('react-router'),
    useNavigate:()=>mockNavigate
}
));
const MockLogin = () => {
    const queryClient = new QueryClient();
    return (<QueryClientProvider client={queryClient}>
        <BrowserRouter>
            <Login />
        </BrowserRouter>
    </QueryClientProvider>);
};
beforeEach(()=>{
    render(
        <MockLogin />
    );
})
describe("Lets test login", () => {
    it("it should render login page", () => {
        expect(screen.getByText("LoginTravelAssistant")).toBeInTheDocument()
    })
    it("should allow user enter home as guest", () => {
            fireEvent.click(screen.getByText("GoAsGuest"));
            expect(mockNavigate).toHaveBeenCalledWith("/")
    })

})